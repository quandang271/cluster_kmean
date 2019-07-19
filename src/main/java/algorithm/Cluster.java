package algorithm;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.FriendModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Cluster {
    public Cluster(int id, FriendModel centroid){
        this.id = id;
        this.centroid = centroid;
        points = new ArrayList<>();
    }

    private List<FriendModel> points;
    private FriendModel centroid;

    @Setter(AccessLevel.NONE)
    private int id;

    public void addPoint(FriendModel point){
        if(point != null)
            points.add(point);
    }

    public void clear(){
        if(points != null)
            points.clear();
    }

    public static void main(String[] args) {
        KMeans k = new KMeans();
        List<Cluster> clusters = new ArrayList<>();
        List<FriendModel> fr = new ArrayList<>();
        clusters = k.createClusters(3);
        fr = k.createPoints(2);
        for(Cluster clus:clusters){
            clus.setPoints(fr);
        }
        System.out.println(clusters);
        for(Cluster clus:clusters){
            clus.clear();
        }
        System.out.println("-----");
        System.out.println(clusters.get(0));
    }
}