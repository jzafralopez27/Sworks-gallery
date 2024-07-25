package com.example.galeria

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.app.Application
import android.graphics.Typeface
import android.media.MediaPlayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.AndroidViewModel
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
    private val musicViewModel: MusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GaleriaApp(musicViewModel)
        }
    }
}

// Código para controlar la navegación (tres pantallas: portada, galería de imágenes y galería de vídeos)

@Composable
fun GaleriaApp(musicViewModel: MusicViewModel) {
    var currentScreen by remember { mutableStateOf("cover") }

    when (currentScreen) {
        
        "cover" -> {
            musicViewModel.resumeMusic()
            IntroScreen(
                onStartGalleryClicked = { currentScreen = "gallery" },
                onStartVideoGalleryClicked = {
                    currentScreen = "videoGallery"
                    musicViewModel.pauseMusic()
                }
            )
        }
        
        "gallery" -> {
            musicViewModel.resumeMusic()
            GalleryScreen(onBackClicked = { currentScreen = "cover" })
        }
        
        "videoGallery" -> VideoGalleryScreen(
            onBackClicked = {
                currentScreen = "cover"
                musicViewModel.resumeMusic()
            }
        )
    }
}

// Empieza el código de la portada

@Composable
fun IntroScreen(onStartGalleryClicked: () -> Unit, onStartVideoGalleryClicked: () -> Unit) {
    
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.perrito),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.18f))
                    .padding(8.dp)
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "SWORKS",
                        color = Color.White,
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Typeface.SERIF)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Jorge Zafra López",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Typeface.SERIF)
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Botón para acceder a la galería de imágenes

            Button(
                onClick = onStartGalleryClicked,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                
                Text(
                    text = "PHOTOS",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Typeface.SERIF)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para acceder a la galería de vídeos

            Button(
                onClick = onStartVideoGalleryClicked,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = "VIDEOS",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Typeface.SERIF)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón de LinkedIn

            Button(
                onClick = {
                    
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/jorge-zafra-l%C3%B3pez-743432224/"))
                    context.startActivity(intent)
                },
                
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                
                Icon(
                    painter = painterResource(R.drawable.linkedin_logo_black),
                    contentDescription = "LinkedInLogo",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "LinkedIn",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Instagram

            Button(
                onClick = {
                    
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/sw0rks_/"))
                    context.startActivity(intent)
                    
                },
                
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                
            ) {
                Icon(
                    painter = painterResource(R.drawable.instagram),
                    contentDescription = "Instagram",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Instagram",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Empieza el código de la galería de imágenes

@Composable
fun GalleryScreen(onBackClicked: () -> Unit) {

    val images = listOf(
        R.drawable.fotikoyo,
        R.drawable.arbol,
        R.drawable.campitooo,
        R.drawable.hands,
        R.drawable.burro,
        R.drawable.haircut,
        R.drawable.bocata,
        R.drawable.beajedrez,
        R.drawable.awela,
        R.drawable.arbol2,
        R.drawable.libelula,
        R.drawable.edifisio,
        R.drawable.drink,
        R.drawable.corcho,
        R.drawable.countryside,
        R.drawable.virgin,
        R.drawable.vaca,
        R.drawable.pescando,
        R.drawable.perrito2,
        R.drawable.redaali,
        R.drawable.torcal,
        R.drawable.negative0_06_06_1_
    )

    var indice by remember { mutableStateOf(0) }

    Image(
        painter = painterResource(R.drawable.arbol2),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "ANALOGIC MEMORIES",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),
            fontFamily = FontFamily(Typeface.SERIF)
        )

        Image(
            painter = painterResource(R.drawable.__7618_moon_black_and_white_moon_and_stars_outline_clip_art_moon_removebg_preview),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 30.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            
            Image(
                painter = painterResource(images[indice]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Button(
                onClick = {
                    
                    indice = if (indice - 1 < 0) images.size - 1 else indice - 1
                },
                
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = MaterialTheme.shapes.small
                
            ) {
                
                Text(
                    text = "Previous",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Typeface.SERIF)
                )
            }

            Button(
                onClick = {
                    
                    indice = (indice + 1) % images.size
                    
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = MaterialTheme.shapes.small
            ) {
                
                Text(
                    text = "Next",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Typeface.SERIF)
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Button(
            onClick = onBackClicked,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            
        ) {
            Text(
                text = "Back",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Typeface.SERIF)
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Shot on Ricoh KR10",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 4.dp),
            fontFamily = FontFamily(Typeface.SERIF)
        )

        Text(
            text = "35mm Ilford HP5 Plus film",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            fontFamily = FontFamily(Typeface.SERIF)
        )
    }
}



// Empieza el código de la galería de vídeos

@Composable
fun VideoGalleryScreen(onBackClicked: () -> Unit) {
    val videos = listOf(
        R.raw.dolor,
        R.raw.blankito,
    )

    var indiceVideos by remember { mutableStateOf(0) }

    Image(
        painter = painterResource(R.drawable.fondovideos),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.drawable.__7618_moon_black_and_white_moon_and_stars_outline_clip_art_moon_removebg_preview),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 30.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        AndroidView(
            factory = { ctx ->
                VideoView(ctx).apply {
                    setVideoURI(Uri.parse("android.resource://${ctx.packageName}/${videos[indiceVideos]}"))
                    start()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = {
                    indiceVideos = if (indiceVideos - 1 < 0) videos.size - 1 else indiceVideos - 1
                },

                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = MaterialTheme.shapes.small

            ) {
                Text(
                    text = "Previous",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Typeface.SERIF)
                )
            }

            Button(
                onClick = {
                    indiceVideos = (indiceVideos + 1) % videos.size
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = "Next",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Typeface.SERIF)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBackClicked,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(
                text = "Back",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Typeface.SERIF)
            )
        }
    }
}

// ViewModel para la música (controlar que se reproduzca, que se pause, que vuelva a sonar...)

class MusicViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private var mediaPlayer: MediaPlayer? = null
    private val cancion = R.raw.cavadov

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mediaPlayer = MediaPlayer.create(context, cancion)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun resumeMusic() {
        mediaPlayer?.start()
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
    }
}