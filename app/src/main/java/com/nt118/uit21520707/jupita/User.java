package com.nt118.uit21520707.jupita;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class User {
    public @Nullable String email;
    public String username;

    public User(){};
    public User(@Nullable String email, @NonNull String username){
        this.email = email;
        this.username = username;
    }
}
