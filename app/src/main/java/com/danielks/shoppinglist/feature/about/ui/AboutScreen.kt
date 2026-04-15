package com.danielks.shoppinglist.feature.about.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.danielks.shoppinglist.app.appinfo.AndroidAppInfoProvider
import com.danielks.shoppinglist.core.designsystem.component.InfoList
import com.danielks.shoppinglist.core.designsystem.component.InfoText
import com.danielks.shoppinglist.feature.about.component.InfoTextSize

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val appInfoProvider = remember { AndroidAppInfoProvider(context) }
    val appInfo = remember { appInfoProvider.get() }

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                "Sobre o app",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(end = 15.dp)
            )
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Home"
            )
        }

        Column (
            modifier = modifier
                .padding(bottom = 12.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            InfoText(
                "App para criação e gerenciamento de listas de compras.",
                size = InfoTextSize.LARGE,
                modifier = modifier.fillMaxWidth()
            )
            InfoList(
                lines = listOf("Suas compras mais organizadas!","Crie listas", "Edite itens", "Finalize e consulte depois", "Atualização de valores em tempo real"),
                size = InfoTextSize.MEDIUM,
                modifier = modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            Column (
                modifier = modifier
                    .border(1.dp, Color.Black)
                    .padding(15.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ){
                InfoText(
                    appInfo.credits,
                    size = InfoTextSize.SMALL,
                    )

                InfoText(
                    "V${appInfo.versionName}",
                    size = InfoTextSize.SMALL,
                )
            }
        }
    }
}
