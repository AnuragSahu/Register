package com.example.anurag.register;

/**
 * Created by anurag on 12/28/2017.
 */

public class RegisterTable {

    String name, nickName, mobileno, collage;
    public RegisterTable()
    {
        name="Not Specified";
        nickName = "Not Specified";
        mobileno = "Not Specified";
        collage = "Not Specified";
    }

    public String getName(){
        return name;
    }

    public String getNickName()
    {
        return nickName;
    }

    public String getMobileno()
    {
        return mobileno;
    }

    public String getCollage()
    {
        return collage;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public void setMobileno(String mobileno)
    {
        this.mobileno = mobileno;
    }

    public void setCollage(String collage)
    {
        this.collage=collage;
    }

}
