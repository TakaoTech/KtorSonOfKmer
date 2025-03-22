package com.takaotech.routing

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(val message: String)

fun Routing.websocketRouting() {
    webSocket("/echo") {
        send("Please enter your name")
        for (frame in incoming) {
            frame as? Frame.Text ?: continue
            val receivedText = frame.readText()
            if (receivedText.equals("bye", ignoreCase = true)) {
                close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
            } else {
                send(Frame.Text("Hi, $receivedText!"))
            }
        }
    }

//    webSocket("/echoSerial") {
//        send("Please enter your name")
//        for (frame in incoming) {
//            frame as? Frame.Text ?: continue
//            val receivedText = frame.readText()
//            if (receivedText.equals("bye", ignoreCase = true)) {
//                close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
//            } else {
//                sendSerialized(MessageResponse(receivedText))
//            }
//        }
//    }

    val messageResponseFlow = MutableSharedFlow<MessageResponse>()
    val sharedFlow = messageResponseFlow.asSharedFlow()

    webSocket("/echoShared") {
        send("You are connected to WebSocket!")

        val job = launch {
            sharedFlow.collect { message ->
                send(message.message)
            }
        }

        runCatching {
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val receivedText = frame.readText()
                    val messageResponse = MessageResponse(receivedText)
                    messageResponseFlow.emit(messageResponse)
                }
            }
        }.onFailure { exception ->
            println("WebSocket exception: ${exception.localizedMessage}")
        }.also {
            job.cancel()
        }
    }
}
