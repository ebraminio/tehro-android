package dev.yasan.metro.tehran.ui.composable.screen.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.yasan.metro.tehran.R
import dev.yasan.metro.tehran.ui.composable.common.TehError
import dev.yasan.metro.tehran.ui.composable.common.TehProgress
import dev.yasan.metro.tehran.ui.composable.common.TehTitle
import dev.yasan.metro.tehran.ui.composable.screen.home.modules.LineItem
import dev.yasan.metro.tehran.ui.theme.grid
import dev.yasan.metro.tehran.util.Resource

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val lines = viewModel.lines.observeAsState()

    if (lines.value is Resource.Initial) {
        viewModel.loadLines()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        item {
            TehTitle(title = stringResource(id = R.string.app_name))
        }

        when (lines.value) {
            is Resource.Error -> {
                item {
                    TehError {
                        viewModel.loadLines()
                    }
                }
            }
            is Resource.Success -> {

                item {
                    Spacer(modifier = Modifier.requiredHeight(grid()))
                }

                items(lines.value!!.data!!) { line ->
                    LineItem(line = line)
                }

            }
            else -> {
                item {
                    TehProgress()
                }
            }
        }

    }

}

@Preview(name = "Home Screen")
@Composable
fun HomeScreenPreview() {
    val viewModel: HomeViewModel = hiltViewModel()
    HomeScreen(viewModel = viewModel)
}