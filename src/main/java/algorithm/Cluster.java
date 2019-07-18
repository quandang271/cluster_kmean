package algorithm;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.FriendModel;

import java.util.List;

@Getter
@Setter
@ToString
public class Cluster {
    private List<FriendModel> points;
    private FriendModel centroid;

    @Setter(AccessLevel.NONE)
    private int id;

    public void clear(){
        points.clear();
    }
}
