package com.ravi.jot.presentation.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.ravi.jot.data.JotEntry
import com.ravi.jot.presentation.components.RichTextT
import com.ravi.jot.presentation.theme.B
import com.ravi.jot.presentation.theme.White
import com.ravi.jot.presentation.vm.NewJotVM

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewJot(
    vm: NewJotVM,
    jotEntryId: Int,
    e: String,
    month: String,
    date: Int,
    goBack: () -> Unit
) {

    val richTextState = rememberRichTextState()
    val context = LocalContext.current
    val focusRequest = remember { FocusRequester() }

    val photoPLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                try {
                    context.contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                vm.sImageURI(uri.toString())
            }
        }
    )

    val jotEntry by vm.getJotEntrybyId(
        i = jotEntryId,
        e = e,
        month = month,
        date = date
    ).collectAsState(
        JotEntry(
            e = e,
            month = month,
            date = date
        )
    )

    LaunchedEffect(jotEntry) {
        if (jotEntryId != -1) {
            richTextState.setText(jotEntry.content)
            vm.sImageURI(uri = jotEntry.photoURI)
        }
        if (jotEntryId == -1) {
            focusRequest.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "${jotEntry.date} ${jotEntry.month.uppercase()} ${jotEntry.e}"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            val jotToAdd = jotEntry.copy(
                                content = richTextState.toText(),
                                photoURI = vm.imageURI
                            )
                            vm.addJotEntry(jotToAdd)
                            goBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { vm.showM() }
                    ) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                    }
                    DropdownMenu(
                        expanded = vm.more,
                        onDismissRequest = { vm.hM() }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Trash") },
                            onClick = {
                                vm.removeJotEntry(jotEntry)
                                goBack()
                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (WindowInsets.isImeVisible) {
                RichTextT(
                    //                    state = richTextState,
                    getImage = {
                        photoPLaunch.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
        }
    ) { p ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(p)
        ) {
            item {
                RichTextEditor(
                    state = richTextState,
                    modifier = Modifier
                        .focusRequester(focusRequest)
                        .fillMaxWidth(),
//                        .weight(1f),
                    colors = RichTextEditorDefaults.richTextEditorColors(
                        containerColor = White,
                        cursorColor = B,
                        unfocusedIndicatorColor = White,
                        focusedIndicatorColor = White
                    )
                )
            }

            if (vm.imageURI != "") {
                item {
                    AsyncImage(
                        model = vm.imageURI.toUri(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}