package com.ravi.jot.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Photo
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.ravi.jot.data.JotDatabase
import com.ravi.jot.data.JotEntry
import com.ravi.jot.data.JotEntryRepo
import com.ravi.jot.ui.theme.B
import com.ravi.jot.ui.theme.White
import com.ravi.jot.ui.vm.NewJotVM
import com.ravi.jot.ui.vmf.NewJotVMF

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewJot(
    jotDB: JotDatabase,
    jotEntryId: Int,
    e: String,
    month: String,
    date: Int,
    goBack: () -> Unit
) {

    val jedao = jotDB.jotEntryDao()
    val jerepo = remember { JotEntryRepo(jedao) }
    val vmf = remember { NewJotVMF(jerepo) }
    val vm: NewJotVM = viewModel(factory = vmf)

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
                vm.sImageURI = uri
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
            vm.sImageURI = jotEntry.photoURI.toUri()
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
                                photoURI = vm.sImageURI.toString()
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

            vm.sImageURI?.let { uri ->
                item {
                    AsyncImage(
                        model = uri,
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

@Composable
fun RichTextT(
//    state: com.mohamedrejeb.richeditor.model.RichTextState,
    getImage: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF9F8FE))
            .imePadding()
    ) {
        IconButton(
            onClick = { getImage() }
        ) {
            Icon(
                imageVector = Icons.Default.Photo,
                contentDescription = ""
            )
        }
//        IconButton(
//            onClick = { state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold)) },
//            colors = if (state.currentSpanStyle.fontWeight == FontWeight.Bold)
//                IconButtonDefaults.iconButtonColors(
//                    containerColor = B,
//                    contentColor = White
//                ) else IconButtonDefaults.iconButtonColors()
//        ) {
//            Icon(
//                imageVector = Icons.Default.FormatBold,
//                contentDescription = ""
//            )
//        }
    }
}