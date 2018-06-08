package me.uport.android.profile

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;

class UserProfileViewModel : ViewModel() {
    val avatarUrl = MutableLiveData<String>().apply { value = "" }
    val username = MutableLiveData<String>().apply { value = "" }
}
