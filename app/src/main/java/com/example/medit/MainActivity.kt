package com.example.medit
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource
import com.example.medit.ui.theme.MeditTheme
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeditTheme {

                var showSplash by remember { mutableStateOf(true) }
                var showOnboarding by remember { mutableStateOf(true) }
                var showRegistration by remember { mutableStateOf(false) }
                var menu by remember { mutableStateOf(false) }
                var listen by remember { mutableStateOf(false) }
                var loggedIn by remember { mutableStateOf(false) }
                var showProfile by remember { mutableStateOf(false) }

                when {
                    showSplash -> {
                        SplashScreen()
                        LaunchedEffect(Unit) {
                            kotlinx.coroutines.delay(2000)
                            showSplash = false
                        }
                    }

                    showOnboarding -> {
                        OnboardingScreen(
                            onContinueClick = { showOnboarding = false },
                            onRegisterClick = {
                                showOnboarding = false
                                showRegistration = true
                            }
                        )
                    }

                    showRegistration -> {
                        RegisterScreen()
                    }
                    showProfile -> {
                        ProfileScreen(onProfileClick = {
                            showProfile = true

                        })
                    }
                    listen -> {
                        Listen()
                    }
                    menu -> {
                        Menu()
                    }

                    !loggedIn -> {
                        LoginScreen(
                            onSignInClick = { email, password ->

                                loggedIn = true
                            },
                            onRegisterClick = {
                                showRegistration = true
                            },
                            onProfileClick = {
                                showProfile = true

                            }
                        )
                    }


                    else -> {
                        MainScreen(onProfileClick = {
                            showProfile = true }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun SplashScreen() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.splashscren),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }

    @Composable
    fun OnboardingScreen(onContinueClick: () -> Unit, onRegisterClick: () -> Unit) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.splashscren1),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.TopCenter)
                    .size(500.dp)

            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(170.dp))
                    Text(
                        "ПРИВЕТ",
                        style = TextStyle(fontSize = 34.sp, fontWeight = FontWeight.Bold),
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 150.dp)


                    )
                    Text(
                        "Наслаждайся отборочными.\n Будь внимателен.\n" +
                                "Делай хорошо.",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        color = Color.White,
                        modifier = Modifier

                    )
                    Spacer(modifier = Modifier.height(100.dp))



                    Button(
                        onClick = onContinueClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C9A92)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(321.dp)
                            .height(61.dp),

                        content = {
                            Text(text = "Войти в аккаунт",
                                color = Color.White,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Normal,)
                        }
                    )


                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(onClick = onRegisterClick) {
                        Text(text = "Ещё нет аккаунта? Зарегистрируйтесь", color = Color.White)
                    }
                }
            }
        }
    }

    @Composable
    fun LoginScreen(
        onSignInClick: (String, String) -> Unit,
        onRegisterClick: () -> Unit,
        onProfileClick: () -> Unit
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var emailError by remember { mutableStateOf(false) }
        var passwordError by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF253334))
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Логотип
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp)
                )


                Text(
                    text = "Sign in",
                    color = Color.White,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .padding(top = 180.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = false
                    },
                    label = { Text("Email", color = Color.White) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
                if (emailError) {
                    Text(text = "Пожалуйста, введите Email", color = Color.Red, fontSize = 12.sp)
                }

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = false
                    },
                    label = { Text("Password", color = Color.White) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
                if (passwordError) {
                    Text(text = "Пожалуйста, введите пароль", color = Color.Red, fontSize = 12.sp)
                }

                Button(
                    onClick = {
                        emailError = email.isEmpty()
                        passwordError = password.isEmpty()

                        if (!emailError && !passwordError) {
                            onSignInClick(email, password)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C9A92)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(321.dp)
                        .height(61.dp)
                ) {
                    Text(text = "Sign In", color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Normal,)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Register",
                    color = Color.White,
                    modifier = Modifier
                        .clickable { onRegisterClick() }
                        .padding(top = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onProfileClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C9A92)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(321.dp)
                        .height(61.dp)
                ) {
                    Text(text = "Profile", color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Normal,)
                }
            }
        }
    }





    @Composable
    fun MainScreen(onProfileClick: () -> Unit) {
        Box(
            modifier = Modifier
                .background(color = Color(0xFF253334))
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Первая строка с изображениями
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hamburger),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .padding(start = 15.dp)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop

                    )
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = null,
                        modifier = Modifier
                            .size(45.dp)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "C возвращением, Эмиль!",
                    color = Color.White,
                    fontSize = 30.sp
                )
                Text(
                    text = "Каким ты себя ощущаешь сегодня?",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 22.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Вторая строка с изображениями
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.first),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.relaxmood),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.focusmood),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.anxious),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.rectangle),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )


                    Text(
                        text = "Заголовок блока",
                        color = Color.Black,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 25.dp)
                            .padding(top = 20.dp)
                    )
                    Text(
                        text = "Кратенькое описание\nблока с двумя строчками",
                        color = Color.Black,
                        fontSize = 17.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 25.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.picture),
                        contentDescription = null,
                        modifier = Modifier
                            .size(210.dp)
                            .align(Alignment.CenterEnd)
                            .padding(start = 20.dp)
                    )


                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 25.dp)
                            .padding(bottom = 10.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253334))
                    ) {
                        Text(text = "подробнее")
                    }
                }


                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.rectangle),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    Text(
                        text = "Заголовок блока",
                        color = Color.Black,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 25.dp)
                            .padding(top = 20.dp)
                    )
                    Text(
                        text = "Кратенькое описание\nблока с двумя строчками",
                        color = Color.Black,
                        fontSize = 17.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 25.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.picture2),
                        contentDescription = null,
                        modifier = Modifier
                            .size(210.dp)
                            .align(Alignment.CenterEnd)
                            .padding(start = 20.dp)
                    )


                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 25.dp)
                            .padding(bottom = 10.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF253334))
                    ) {
                        Text(text = "подробнее")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),

                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)

                    )
                    Image(
                        painter = painterResource(id = R.drawable.sound),
                        contentDescription = null,
                        modifier = Modifier
                            .size(27.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.profile1),
                        contentDescription = null,
                        modifier = Modifier
                            .size(27.dp)
                            .clickable { onProfileClick() }

                    )
                }

            }
        }
    }

    @Composable
    fun RegisterScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF253334)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Тут будет регистрация",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    @Composable
    fun Menu() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF253334)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Тут будет меню",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    @Composable
    fun Listen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF253334)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Тут будет \n" +
                        "прослушивание",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    @Composable
    fun ProfileScreen(onProfileClick: () -> Unit) {
        Box(
            modifier = Modifier
                .background(color = Color(0xFF253334))
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hamburger),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .padding(start = 15.dp)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop

                    )
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "exit",
                        color = Color.White,
                        fontSize = 17.sp,
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .align(Alignment.CenterVertically),
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))



                Image(
                    painter = painterResource(id = R.drawable.profilephoto),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Эмиль",
                    color = Color.White,
                    fontSize = 34.sp,
                    modifier = Modifier
                        .padding(end = 15.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.firstp),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterEnd)
                            .padding(start = 20.dp),
                        contentScale = ContentScale.Crop
                    )

                    // Текст поверх картинки
                    Text(
                        text = "11:11",
                        color = Color.White,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 25.dp)
                            .padding(top = 20.dp)

                    )

                    Image(
                        painter = painterResource(id = R.drawable.secondp),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterEnd)
                            .padding(start = 20.dp),
                        contentScale = ContentScale.Crop
                    )


                    // Первая строка с изображениями
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),

                        horizontalArrangement = Arrangement.SpaceEvenly, // Равномерно распределяем картинки
                        verticalAlignment = Alignment.CenterVertically // Центрируем по вертикали
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.main),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(70.dp)
                                .clickable { onProfileClick() }

                        )
                        Image(
                            painter = painterResource(id = R.drawable.sound),
                            contentDescription = null,
                            modifier = Modifier
                                .size(27.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)

                        )
                    }

                }
            }
        }
    }



    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MeditTheme {
            MainScreen(onProfileClick = { })
        }
    }
}