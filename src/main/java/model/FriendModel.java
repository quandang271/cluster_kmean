package model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter @ToString
public class FriendModel {
//    private List<String> listFriendIDs;
//
//    private long longHometown;
//    private long latHometown;
//    private long longResidenceLocation;
//    private long latResidenceLocation;
//
//    private List<String> listSchools;
//    private List<String> listCompanys;
//    private List<String> listGroupIDs;

    public FriendModel(double hometownInverseDistance,double residenceInverseDistance, double friendsMutualNum, double groupsMutualNum, double reactionsNum){
        this.hometownInverseDistance = hometownInverseDistance;
        this.residenceInverseDistance = residenceInverseDistance;
        this.friendsMutualNum = friendsMutualNum;
        this.groupsMutualNum = groupsMutualNum;
        this.reactionsNum = reactionsNum;
    }
    // normalize [0-1], distance of (hometown and residence are inversed)
    private double hometownInverseDistance = 0;
    private double residenceInverseDistance = 0;
    private double friendsMutualNum = 0;
    private double groupsMutualNum = 0;
    private double reactionsNum = 0;

    private int clusterID = 0;

    public void setClusterID(int clusterID) {
        this.clusterID = clusterID;
    }

    //Calculates the distance between two points.
    public static double distance(FriendModel point, FriendModel centroid) {
        return Math.sqrt( Math.pow((centroid.getHometownInverseDistance() - point.getHometownInverseDistance()), 2)
                + Math.pow((centroid.getResidenceInverseDistance() - point.getResidenceInverseDistance()), 2)
                +Math.pow((centroid.getFriendsMutualNum() - point.getFriendsMutualNum()), 2)
                +Math.pow((centroid.getGroupsMutualNum() - point.getGroupsMutualNum()), 2)
                +Math.pow((centroid.getReactionsNum() - point.getReactionsNum()), 2)
        );
    }

    public static void main(String[] args) {
        FriendModel f1 = new FriendModel(0,0.2,0.3,0.4,0.5);
        FriendModel f2 = new FriendModel(0.4,0.2,0.3,0.4,0.5);
        System.out.println(distance(f1,f2));
    }
}
