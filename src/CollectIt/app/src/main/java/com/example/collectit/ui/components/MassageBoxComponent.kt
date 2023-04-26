package com.example.collectit.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.collectit.ui.theme.CollectItTheme

class MassageBoxComponent {
    companion object {
        @ExperimentalMaterial3Api
        @Composable
        fun MassageBox(

        ){
        }

        @ExperimentalMaterial3Api
        @Preview(showBackground = true)
        @Composable
        fun prevMassageBox(){
            CollectItTheme {
                MassageBox()
            }
        }
    }
}