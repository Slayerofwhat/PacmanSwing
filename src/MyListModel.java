import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyListModel extends AbstractListModel {

    Player dummy = new Player("No saves", 0);

    private List<Player> players = new ArrayList<>(List.of(dummy));

    public MyListModel(){
    };

    @Override
    public int getSize() {
        return players.size();
    }

    @Override
    public Object getElementAt(int index) {
        return players.get(index).getName() + " - " + players.get(index).getScore();
    }

    public void addPlayer(Player player){
        if (players.get(0) == dummy) players.remove(dummy);
        boolean isAdded = false;
        for (int i = 0; i < players.size(); i++){
            if (Objects.equals(players.get(i).getName(), player.getName()) && players.get(i).getScore() < player.getScore()){
                players.remove(i);
                players.add(player);
                isAdded = true;
                break;
                //fireContentsChanged(this, 0, getSize() - 1);
            }
        }
        if (!isAdded) {
            players.add(player);
        }
    }


}
