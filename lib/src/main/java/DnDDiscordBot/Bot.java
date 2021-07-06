package DnDDiscordBot;

//Imports
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Bot extends ListenerAdapter
{
    public static void main(String[] args) throws LoginException, IOException
    {
    	Path filePath = Paths.get("token.txt");
        String token = Files.readString(filePath);
        // args[0] should be the token
        // We only need 2 intents in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.
        JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
            .addEventListeners(new Bot())
            .build();
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        String content = msg.getContentRaw().toUpperCase();
        if(content.startsWith("!ROLL"))
        {
        	//replace with REGEX later
        	int indexD = content.indexOf("D");
        	Random r = new Random();
        	//If string is not properly formatted
        	if(indexD== -1)
        	{
        		event.getChannel().sendMessage("ERROR: roll command format \"!roll{numRolls}d{sidesOfDie}").queue();
        	}
        	//If rollAmt is not specified, roll once
        	if(indexD==5)
        	{
        		event.getChannel().sendMessage("Roll for " + event.getAuthor().getName() + ": "  + (r.nextInt(Integer.parseInt(content.substring(6)))+1) ).queue();
        	}
        	//If string is properly formatted
        	String rollAmt = content.substring(5,indexD);
        	String rollDie = content.substring(indexD+1);
        	int sum = 0;
        	for(int i = 0; i < Integer.parseInt(rollAmt); i++)
        	{
        		int result = r.nextInt(Integer.parseInt(rollDie))+1;
        		event.getChannel().sendMessage("Roll #" + (i + 1) + ": " + result  ).queue();
        		sum += result;
        	}
        	event.getChannel().sendMessage("Roll Total for " + event.getAuthor().getName() + ": " + sum).queue();
        }
        	
    }
}
