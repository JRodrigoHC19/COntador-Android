package com.miempresa.labapp13

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ControllerScreeen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var password by remember { mutableStateOf("")}
    val auth = Firebase.auth
    val contexto = LocalContext.current
    var email by remember { mutableStateOf("") }
    

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#0F130C"))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.padding(vertical = 100.dp))

        Text(
            text = "Login",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp, start = 25.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 50.sp,

        )

        Text(
            text = "Por favor inicie sesión para continuar",
            color = Color.DarkGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, start = 25.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.DarkGray, fontWeight = FontWeight.Bold) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.DarkGray, fontWeight = FontWeight.Bold) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Button(
            onClick = {
                try {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(contexto, "Procesando...", Toast.LENGTH_SHORT).show()
                                navController.navigate("lobby/$email/$password")
                            } else {
                                Toast.makeText(contexto,"Este usuario no está registrado.", Toast.LENGTH_SHORT).show()
                            }
                        }
                } catch (e : Exception){
                    if (email.isEmpty() || password.isEmpty()){
                        Toast.makeText(contexto, "Debes Indicar las Credenciales", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(contexto, "Error al Intentar Logearte", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray
            )
        ) {
            Text(
                text = "Sign In",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }


        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {

            Column{
                Text(
                    text = "No tienes una cuenta? ",
                    fontWeight = FontWeight.Bold,
                    color = Color(android.graphics.Color.parseColor("#ECEFF1")),
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 60.dp)
                )
            }

            Column {
                Text(
                    text = "Registrate aquí",
                    textAlign = TextAlign.Center,
                    color = Color.Green,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.clickable(onClick = {navController.navigate("register")})
                )
            }
        }



    }
}


@Composable
fun ControllerScreeen(){
    val navController = rememberNavController()
    val contexto = LocalContext.current
    NavHost(navController = navController, startDestination = "login"){
        composable("login"){ MainScreen(navController)}

        composable(
            route = "lobby/{email}/{password}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) {
            LobbyScreen(navController = navController)
    }

        composable("register"){ RegistrarScreen(navController = navController,contexto)}
    }
}


// buildAnnotatedString {
//                withStyle(style = SpanStyle(color = Color.Green)) {
//                    append("L")
//                }
//                append("OGIN")
//            },