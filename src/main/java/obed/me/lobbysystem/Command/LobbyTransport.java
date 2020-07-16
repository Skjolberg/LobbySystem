package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import obed.me.lobbysystem.Lobbysystem;

public class LobbyTransport extends Command {
    public LobbyTransport(String name, String permission) {
        super(name , permission , "lobbytransport");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(pp.hasPermission(this.getPermission())){
                if(args.length <=1){
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.transport.arguments"));
                    return;
                }
                ServerInfo target = Lobbysystem.getInstance().getProxy().getServerInfo(args[0]);
                ServerInfo teleport = Lobbysystem.getInstance().getProxy().getServerInfo(args[1]);
                if(!Lobbysystem.getLobbys().contains(target)){
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.transport.error").replaceAll("%server%", target.getName()));
                    return;
                }
                if(!Lobbysystem.getLobbys().contains(teleport)){
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.transport.error").replaceAll("%server%", teleport.getName()));
                    return;
                }
                for(ProxiedPlayer ppall : target.getPlayers()){
                    ppall.connect(teleport);
                }
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.transport.sucess").replaceAll("%target_amount%", Integer.toString(target.getPlayers().size()))
                .replaceAll("%target%", target.getName())
                .replaceAll("%objetive%", teleport.getName()));



            } else {
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));
            }
        }
    }
}
