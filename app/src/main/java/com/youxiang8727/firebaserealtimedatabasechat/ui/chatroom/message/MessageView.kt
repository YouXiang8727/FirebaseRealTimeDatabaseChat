package com.youxiang8727.firebaserealtimedatabasechat.ui.chatroom.message

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun MessageView(
    modifier: Modifier = Modifier,
    messageUiModel: MessageUiModel
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = messageUiModel.contentAlignment()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (messageUiModel.showSenderInfo) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier.size(32.dp)
                            .clip(CircleShape),
                        model = messageUiModel.senderAvatar.drawable,
                        contentDescription = null,
                    )

                    Text(
                        text = messageUiModel.senderName,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            Card(
                colors = messageUiModel.messageCardColors()
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = messageUiModel.message,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}