package fragments

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.C.VIDEO_CHANGE_FRAME_RATE_STRATEGY_OFF
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView

import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import functions.retrofit.ModelForRequest
import retrofit.ModelForGet
import retrofit.postParam

@Composable
fun VideoReciever(currentStake: MutableState<ModelForGet>) {
    var button_clicked= remember {
        mutableStateOf(false)
    }

Column(modifier = Modifier
    .fillMaxSize()
    .background(Color.Black)) {
    if(button_clicked.value==false){
        video_Reciever(currentStake = currentStake, button_clicked =button_clicked )}
    else{
        Box_text(button_clicked,currentStake)
       Row(Modifier.height(350.dp).fillMaxHeight()) {Displa(button_clicked =button_clicked)}
    }


    Row(Modifier.padding(top=40.dp, bottom = 10.dp)){
        btn( button_clicked,Color =Color.Green,ModelForRequest("Ok","Accept","2"),currentStake.value.mobile.toString())
        btn(button_clicked, Color =Color.Red,ModelForRequest("","Reject","0"),currentStake.value.mobile.toString())
    }

   
    }

}






@Composable
fun btn(button_clicked:MutableState<Boolean>,Color:Color,data:ModelForRequest,value:String){
    Row( modifier = Modifier
        .requiredSize(width = 320.dp, height = 245.dp)
        .padding(bottom = 175.dp, end = 30.dp)
        .clip(shape = RoundedCornerShape(40.dp))
        .fillMaxSize()
        .background(Color)
        .clickable {
            if (button_clicked.value == true) {
                //postParam(value, data)
                button_clicked.value = false
            } else {
                button_clicked.value = true
            }

        }){

    }
}

@Composable
fun list_of_colors(how_much:Int){
    val colores= arrayOf(Color(255, 64, 64),Color.Red,Color(255, 116, 0),Color(255, 222, 64),Color.Green,Color.Cyan)
    for (i in 0..how_much){
    Row(modifier = Modifier
        .padding(end = 20.dp)
        .requiredSize(width = 20.dp, height = 20.dp)
        .clip(
            RoundedCornerShape(10.dp)
        )
        .background(colores[i])
        .fillMaxSize()){}}
}

@Composable
fun video_Reciever(currentStake: MutableState<ModelForGet>, button_clicked: MutableState<Boolean>){
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .padding(top = 200.dp, bottom = 400.dp)
        .requiredSize(520.dp)
        .fillMaxSize()) {
        val context = LocalContext.current
        val videoUrl = "http://192.168.1.6:3000/video/${currentStake.value.mobile.toString()}"

        val exoPlayer = remember(context) {
            SimpleExoPlayer.Builder(context).setVideoChangeFrameRateStrategy(
                VIDEO_CHANGE_FRAME_RATE_STRATEGY_OFF
            ).build().apply {
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, context.packageName)
                )

                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)))

                this.prepare(source)
            }
        }

        AndroidView(factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        })
        Displa(button_clicked = button_clicked)
    }}

@Composable
fun Box_text(button_clicked: MutableState<Boolean>,currentStake: MutableState<ModelForGet>){
        Column(
            modifier = Modifier
                .padding(start = 5.dp, top = 100.dp, bottom = 200.dp, end = 5.dp).height(200.dp).fillMaxWidth()
        ) {
            Text(text = "Выберете цвет для идентификации отказа", textAlign = TextAlign.Center)
        }


}

@Composable
fun Displa(button_clicked: MutableState<Boolean>){
    Row(
        modifier = Modifier
            .padding(top = 20.dp, end = 2.dp).height(25.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        if (button_clicked.value == false) {
            list_of_colors(0)
        } else {
            list_of_colors( 5)
        }
}}