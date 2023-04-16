package net.minecraft.src;

public class AccountNBTStorage
{
    public String name;
    public String pass;

    public AccountNBTStorage(String s, String s1)
    {
        name = s;
        pass = s1;
    }

    public NBTTagCompound getCompoundTag()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("name", name);
        nbttagcompound.setString("pass", pass);
        return nbttagcompound;
    }

    public static AccountNBTStorage createAccountNBTStorage(NBTTagCompound nbttagcompound)
    {
        return new AccountNBTStorage(nbttagcompound.getString("name"), nbttagcompound.getString("pass"));
    }
}
