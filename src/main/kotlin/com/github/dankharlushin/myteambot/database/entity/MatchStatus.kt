package com.github.dankharlushin.myteambot.database.entity

enum class MatchStatus {
    NS,        //Not Started
    LIVE,      //Live
    HT,        //Half-Time
    FT,        //Full-Time
    ET,        //Extra-Time
    PEN_LIVE,  //Penalty Shootout
    AET,       //Finished after extra time
    BREAK,     //Regular time finished
    FT_PEN,    //Full-Time after penalties
    CANCL,     //Canceled
    POSTP,     //PostPoned
    INT,       //Interrupted
    ABAN,      //Abandoned
    SUSP,      //Suspended
    TBA,       //To Be Announced
    AWARDED,   //Awarded
    DELAYED,   //Delayed
    WO,        //Walk Over
    AU,        //Awaiting Updates
    Deleted    //Deleted
}