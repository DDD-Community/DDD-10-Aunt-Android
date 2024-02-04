package com.aunt.opeace.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.aunt.opeace.home.HomeActivity
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : ComponentActivity(), LoginInterface {
    private val loginResultHandler = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // NOTE : 서버 통신 후 ? 완료 되면 Screen에서 SignupActivity로 이동
            println("$TAG loginResultHandler() success: ${result.data}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }

    override fun googleLogin() {
        val request = GetSignInIntentRequest.builder()
            .setServerClientId(CLIENT_ID)
            .build()

        Identity.getSignInClient(this)
            .getSignInIntent(request)
            .addOnSuccessListener { result ->
                loginResultHandler.launch(
                    IntentSenderRequest.Builder(result.intentSender)
                        .build()
                )
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    override fun goHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    companion object {
        private val TAG = this::class.java.simpleName
        private const val CLIENT_ID =
            "940949450606-7r897jb3nopecgcas6vk0a4lsqg05dj1.apps.googleusercontent.com"
    }
}