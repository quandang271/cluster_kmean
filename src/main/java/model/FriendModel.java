package model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter @ToString
public class FriendModel {
    private List<String> listFriendIDs;

    private long longHometown;
    private long latHometown;
    private long longResidenceLocation;
    private long latResidenceLocation;

    private List<String> listSchools;
    private List<String> listCompanys;
    private List<String> listGroupIDs;

    private int friendsMutualNum;


    private int cluster_number = 0;

    public void setCluster_number(int cluster_number) {
        this.cluster_number = cluster_number;
    }

    //Calculates the distance between two points.
    public static double distance(FriendModel p, FriendModel centroid) {
        double result = 0;
     //   return Math.sqrt(Math.pow((centroid.getY() - p.getY()), 2) + Math.pow((centroid.getX() - p.getX()), 2));
        return result;
    }
}
