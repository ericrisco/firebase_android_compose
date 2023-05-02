package com.example.firebaseandroid.viewmodels.tweets

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseandroid.models.Tweet
import com.example.firebaseandroid.models.TweetType
import com.example.firebaseandroid.viewmodels.storage.StorageViewModelInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DummyTweetsViewModel : TweetsViewModelInterface, ViewModel() {

    var dummyTweets = listOf(
        Tweet("1", "Rubius", TweetType.TEXT, "¡Bienvenidos, flipaos, al directo más esperado!", "20230101090000"),
        Tweet("4", "Auronplay", TweetType.TEXT, "Aquellos que hacen el bien son aquellos que no buscan reconocimiento", "20230101120000"),
        Tweet("5", "TheGrefg", TweetType.IMAGE, "https://pbs.twimg.com/media/DpGF4aRX4AAdBYw.jpg", "20230101130000"),
        Tweet("6", "Willyrex", TweetType.TEXT, "¡Ey muy buenas a todos! Aquí Willyrex comentando.", "20230101140000"),
        Tweet("11", "Alexby11", TweetType.TEXT, "El éxito no es la clave de la felicidad. La felicidad es la clave del éxito", "20230101190000"),
        Tweet("15", "Fargan", TweetType.TEXT, "No hay nada imposible, porque los sueños de ayer son las esperanzas de hoy y pueden convertirse en realidad mañana", "20230101230000"),
        Tweet("17", "Lolito", TweetType.TEXT, "El éxito no es la clave de la felicidad. La felicidad es la clave del éxito", "20230102010000"),
        Tweet("21", "estikk", TweetType.TEXT, "Me he enfrentado a innumerables retos de comida, pero hoy estoy aquí para comerme 200 aros de cebolla", "20230101090000"),
        Tweet("22", "thegrefg", TweetType.TEXT, "TheGrefg siempre gana", "20230101100000"),
        Tweet("23", "auronplay", TweetType.IMAGE, "https://phantom-marca.unidadeditorial.es/3ed1bd6925d36216d296d8c5e44b321a/crop/8x0/878x490/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/23/16429668459960.jpg", "20230101110000"),
        Tweet("24", "willyrex", TweetType.TEXT, "¡Ey muy buenas a todos! Aquí Willyrex comentando.", "20230101120000"),
        Tweet("27", "Rubius", TweetType.TEXT, "La risa es la mejor medicina", "20230102030000"),
        Tweet("29", "TheGrefg", TweetType.TEXT, "Nunca renuncies a tus sueños", "20230102050000"),
        Tweet("31", "Auronplay", TweetType.TEXT, "Nunca te rindas. Los milagros ocurren cada día", "20230102070000"),
        Tweet("33", "Willyrex", TweetType.TEXT, "El éxito no se trata solo de lo que logras en la vida, sino de lo que inspiras a otros a hacer", "20230102090000"),
        Tweet("36", "Alexby11", TweetType.TEXT, "El éxito es el resultado de un esfuerzo constante", "20230102120000"),
        Tweet("37", "Fargan", TweetType.TEXT, "La felicidad es un perfume que no puedes derramar sobre otros sin obtener unas gotas en ti mismo", "20230102130000"),
        Tweet("41", "Lolito", TweetType.TEXT, "El éxito no se mide por la cantidad de dinero que tienes, sino por la cantidad de vidas que has tocado", "20230102170000"),
        Tweet("43", "estikk", TweetType.TEXT, "La vida es corta, sonríe mientras todavía tengas dientes", "20230102190000"),
        Tweet("44", "thegrefg", TweetType.TEXT, "La única forma de hacer un gran trabajo es amar lo que haces", "20230102200000"),
    )

    override fun fetchTweets(onSuccess: (List<Tweet>) -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            val result = async {
                dummyTweets
            }
            try {
                onSuccess(result.await())
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    override fun postTweet(tweet: Tweet, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                onSuccess()
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}