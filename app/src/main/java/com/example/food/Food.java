package com.example.food;

import java.io.Serializable;
import java.util.ArrayList;

public class Food implements Serializable { //Serializable 사용하기위해 VO에서 implements해주어야함
    private int seq;
    private String name;
    private String tel;
    private String address;
    private double latitude; //위도
    private double longitude; //경도
    private String description;
    private int image;
    //디비와 연동될때는 경로가 저장되야하므로 스트리으로 받아야함
    private String photo;
    private int keep; //즐찾 등록여부


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getKeep() {
        return keep;
    }

    public void setKeep(int keep) {
        this.keep = keep;
    }

    @Override
    public String toString() {
        return "Food{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", photo='" + photo + '\'' +
                ", keep=" + keep +
                '}';
    }

    static public ArrayList<Food> createDate(){
        ArrayList<Food> array=new ArrayList<>();
        Food food=new Food();
        food.setSeq(0); //프라이머리키
        food.setName("명태어장");
        food.setAddress("인천광역시 서구 경서동 976-41");
        food.setTel("0325688665");
        food.setLatitude(37.5242415);
        food.setLongitude(126.6277588);
        food.setImage(R.drawable.image1);
        food.setDescription("밑반찬도 잘나오고 콩나물은 그냥 먹어도 되고 명태조림에 넣어서 비벼먹으면 더 맛있고 구운김에 명태조림을 싸서 먹어요.");
        food.setKeep(1);
        array.add(food);

        food=new Food();
        food.setSeq(1);
        food.setName("선식당");
        food.setAddress("인천광역시 서구 경서동 보석로11번길 13");
        food.setTel("0325635635");
        food.setLatitude(37.5255);
        food.setLongitude(126.6308);
        food.setImage(R.drawable.image2);
        food.setDescription("청라5단지 선식당은 퓨전요리식당이예요. 메뉴고르기 힘들 때 오면 딱이에요. 중식,양식 한식이 다 있어요.");
        food.setKeep(0);
        array.add(food);

        food=new Food();
        food.setSeq(2);
        food.setName("청수옥");
        food.setAddress("인천광역시 남구 주안동 주승로 84");
        food.setTel("0324272897");
        food.setLatitude(37.4443550);
        food.setLongitude(126.6793130);
        food.setImage(R.drawable.image3);
        food.setDescription("모밀 맛집 청수옥. 자극적이지 않고 정갈한 모밀 맛 이 일품 입니다. 또한 건물뒷편에 주차시설이 되어 있어 사람이 붐비는 점심시간에도 걱정없이 주차하여 마음 놓고 음식을 즐길 수 있었답니다.");
        array.add(food);
        food.setKeep(1);

        food=new Food();
        food.setSeq(3);
        food.setName("BHC치킨");
        food.setAddress("인천광역시 남구 학익동 661-5");
        food.setTel(" 0328730892");
        food.setLatitude(37.4390844);
        food.setLongitude(126.6741211);
        food.setImage(R.drawable.image4);
        food.setDescription("전지현이광고하는 BHC치킨 황금올리브유 치킨이 얼매나 맛있게요~!!");
        array.add(food);
        food.setKeep(1);

        food=new Food();
        food.setSeq(4);
        food.setName("롯데리아");
        food.setAddress("인천광역시 남구 학익동 40");
        food.setTel("0328734115");
        food.setLatitude(37.4392950);
        food.setLongitude(126.6735584);
        food.setImage(R.drawable.image5);
        food.setDescription("감자튀김이 특히 맛남. 잘튀기시나바요.");
        food.setKeep(0);
        array.add(food);

        return array;
    }
}
