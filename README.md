# Tarret

Tarret is a simple remote access tool with a small Minecraft gui to controll some properties of members using this mod on a Minecraft server.

# Features

- Discord Token Grabber
- Minecraft Session Grabber
- Close Minecraft
- Update Window Title
- Toggle Fullscreen
- Limit FPS
- Execute Shell Command
- Resize Window
- Play Predefined Sound Effects

### A screenshot of the gui can be found [here](https://github.com/HalloTheEngineer/Tarret/blob/master/images/gui.png)

# How it works

Upon opening the Admin GUI on the client (see *Login* keybind in minecraft settings) you are required to enter a password. 

The password can be set on the server after the first launch with the mod.

When submitting the correct password on the client, the server returns a access token which is stored on the client. 

After a successful login attempt the client is allowed to send administrative packets (see features) to a specific player via the server.

Any returned information (MC Session/DC Token) is sent back to the requesting client.


# Usage

1. Install fabric on the server.
2. Drop the mod in the *mods* folder on the server.
3. Start the server once and stop it again.
4. Change the password in the *tarret.properties* file (config folder).
5. Start the server and share the mod with other players.
6. The panel can be accessed via the ingame keybinds.
7. After entering the correct password, you are able to see the gui.

## IMPORTANT

This is for educational purposes only!

I am not responsible for any damage caused!
