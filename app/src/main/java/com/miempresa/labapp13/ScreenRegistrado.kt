package com.miempresa.labapp13

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.miempresa.labapp13.ui.theme.rojooscur

@Composable
fun LobbyScreen(navController: NavController){
    Column {

        Row (modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()) {
            Text(
                text = "Contador Moderno",
                Modifier.padding(vertical = 16.dp, horizontal = 10.dp),
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Button(
                onClick = { navController.navigate("login")},
                modifier = Modifier
                    .padding(vertical = 13.dp)
                    .padding(start = 25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#282021"))
                )
            ) {
                Text(
                    text = "Log Out",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }


            // Text(text = "Nombre: $nombre", Modifier.padding(vertical = 8.dp))
        }

        Row(
            modifier = Modifier
                .background(Color(android.graphics.Color.parseColor("#0F130C"))),
            // horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CounterScreen()
        }


    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarScreen(navController: NavController, contexto: Context) {
    var EmailNew by remember { mutableStateOf("") }
    var PasswordNew by remember { mutableStateOf("") }

    val registerUser = {
        val auth = Firebase.auth
        try {

            auth.createUserWithEmailAndPassword(EmailNew, PasswordNew)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(contexto, "Procesando...", Toast.LENGTH_SHORT).show()
                        Toast.makeText(contexto, "Usuario creado exitosamente!", Toast.LENGTH_SHORT).show()
                        navController.navigate("login")
                    } else {
                        Toast.makeText(contexto,"Los Datos no son válidos.", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e : Exception){
            if (EmailNew.isEmpty() || PasswordNew.isEmpty()){
                Toast.makeText(contexto, "Debes Indicar las Credenciales", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(contexto, "Error al Intentar Registrarse", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#0F130C"))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.padding(vertical = 50.dp))

        Text(
            text = "Register",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp, start = 25.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 50.sp,

            )

        Text(
            text = "Por favor, registre su cuenta para continuar",
            color = Color.DarkGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 25.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
        )

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = EmailNew, onValueChange = { EmailNew = it },
                label = { Text("Correo", color = Color.DarkGray, fontWeight = FontWeight.Bold) },
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
                value = PasswordNew, onValueChange = { PasswordNew = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("Password", color = Color.DarkGray, fontWeight = FontWeight.Bold) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                )
            )



            Button(
                onClick = { registerUser() },
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 50.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                )
            ) {
                Text(
                    text = "Registrar",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }

            Button(
                onClick = {
                    navController.navigate("login")
                },
                modifier = Modifier.padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(rojooscur)
            ) {
                Text(text = "Regresar")
            }
        }
    }
}


@Composable
fun CounterScreen() {
    var count by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = count.toString(),
            color = Color.White,
            fontSize = 81.sp
        )

        Spacer(modifier = Modifier.height(100.dp))

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                CounterButton(
                    text = "Disminuir",
                    onClick = { if (count > 0) count-- }
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                CounterButton(
                    text = "Aumentar",
                    onClick = { count++ }
                )
            }
        }
    }
}

@Composable
fun CounterButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .padding(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(android.graphics.Color.parseColor("#ae9d96"))
        )
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}