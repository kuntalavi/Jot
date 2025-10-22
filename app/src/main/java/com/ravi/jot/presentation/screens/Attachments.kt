package com.ravi.jot.presentation.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.ravi.jot.data.JotDatabase
import com.ravi.jot.data.JotEntryRepo
import com.ravi.jot.presentation.vm.AttachmentsVM
import com.ravi.jot.presentation.vmf.AttachmentsVMF

@Composable
fun Attachments(jotDB: JotDatabase) {

    val jedao = jotDB.jotEntryDao()
    val jerepo = remember { JotEntryRepo(jedao) }
    val vmf = remember { AttachmentsVMF(jerepo) }
    val vm: AttachmentsVM = viewModel(factory = vmf)

    val photos by vm.photos.collectAsState(
        emptyList()
    )

    val photoState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = photoState,
        modifier = Modifier.fillMaxWidth()
    ) {
        items(photos) { photo ->
            Photo(
                uri = photo.toUri()
            )
        }
    }
}

@Composable
fun Photo(uri: Uri) {
    Image(
        painter = rememberAsyncImagePainter(uri),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}