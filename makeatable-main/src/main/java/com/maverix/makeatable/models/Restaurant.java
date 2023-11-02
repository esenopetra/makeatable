package com.maverix.makeatable.models;

import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.RestStatus;
import com.maverix.makeatable.enums.RoomType;
import com.maverix.makeatable.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private Long mobileNum;

    private String location;
    @Column(unique = true)
    private String email;

    private String imageUrl;
    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantRating> ratings;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalTime openTime;

    private LocalTime closeTime;

    @Enumerated(EnumType.STRING)
    private FoodCategory foodType;

    private Long seatNum;

    private String description;

    @Enumerated(EnumType.STRING)
    private RoomType typeRoom;

    private RestStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Double getAverageRating() {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }

        double totalRating = 0.0;
        for (RestaurantRating rating : ratings) {
            totalRating += rating.getRating();
        }

        return totalRating / ratings.size();
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMobileNum(Long mobileNum) {
        this.mobileNum = mobileNum;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRatings(List<RestaurantRating> ratings) {
        this.ratings = ratings;
    }

    public void setUser(User user) {
        if(user.getUserType() == UserType.MANAGER) {
            this.user = user;
        } else{
            throw new IllegalArgumentException("Only users with UserType Manager can be linked to a restaurant");
        }
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public void setFoodType(FoodCategory foodType) {
        this.foodType = foodType;
    }

    public void setSeatNum(Long seatNum) {
        this.seatNum = seatNum;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypeRoom(RoomType typeRoom) {
        this.typeRoom = typeRoom;
    }

    public void setStatus(RestStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Restaurant)) return false;
        final Restaurant other = (Restaurant) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$fullName = this.getFullName();
        final Object other$fullName = other.getFullName();
        if (this$fullName == null ? other$fullName != null : !this$fullName.equals(other$fullName)) return false;
        final Object this$mobileNum = this.getMobileNum();
        final Object other$mobileNum = other.getMobileNum();
        if (this$mobileNum == null ? other$mobileNum != null : !this$mobileNum.equals(other$mobileNum)) return false;
        final Object this$location = this.getLocation();
        final Object other$location = other.getLocation();
        if (this$location == null ? other$location != null : !this$location.equals(other$location)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$imageUrl = this.getImageUrl();
        final Object other$imageUrl = other.getImageUrl();
        if (this$imageUrl == null ? other$imageUrl != null : !this$imageUrl.equals(other$imageUrl)) return false;
        final Object this$ratings = this.getRatings();
        final Object other$ratings = other.getRatings();
        if (this$ratings == null ? other$ratings != null : !this$ratings.equals(other$ratings)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$openTime = this.getOpenTime();
        final Object other$openTime = other.getOpenTime();
        if (this$openTime == null ? other$openTime != null : !this$openTime.equals(other$openTime)) return false;
        final Object this$closeTime = this.getCloseTime();
        final Object other$closeTime = other.getCloseTime();
        if (this$closeTime == null ? other$closeTime != null : !this$closeTime.equals(other$closeTime)) return false;
        final Object this$foodType = this.getFoodType();
        final Object other$foodType = other.getFoodType();
        if (this$foodType == null ? other$foodType != null : !this$foodType.equals(other$foodType)) return false;
        final Object this$seatNum = this.getSeatNum();
        final Object other$seatNum = other.getSeatNum();
        if (this$seatNum == null ? other$seatNum != null : !this$seatNum.equals(other$seatNum)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$typeRoom = this.getTypeRoom();
        final Object other$typeRoom = other.getTypeRoom();
        if (this$typeRoom == null ? other$typeRoom != null : !this$typeRoom.equals(other$typeRoom)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$updatedAt = this.getUpdatedAt();
        final Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Restaurant;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $fullName = this.getFullName();
        result = result * PRIME + ($fullName == null ? 43 : $fullName.hashCode());
        final Object $mobileNum = this.getMobileNum();
        result = result * PRIME + ($mobileNum == null ? 43 : $mobileNum.hashCode());
        final Object $location = this.getLocation();
        result = result * PRIME + ($location == null ? 43 : $location.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $imageUrl = this.getImageUrl();
        result = result * PRIME + ($imageUrl == null ? 43 : $imageUrl.hashCode());
        final Object $ratings = this.getRatings();
        result = result * PRIME + ($ratings == null ? 43 : $ratings.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $openTime = this.getOpenTime();
        result = result * PRIME + ($openTime == null ? 43 : $openTime.hashCode());
        final Object $closeTime = this.getCloseTime();
        result = result * PRIME + ($closeTime == null ? 43 : $closeTime.hashCode());
        final Object $foodType = this.getFoodType();
        result = result * PRIME + ($foodType == null ? 43 : $foodType.hashCode());
        final Object $seatNum = this.getSeatNum();
        result = result * PRIME + ($seatNum == null ? 43 : $seatNum.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $typeRoom = this.getTypeRoom();
        result = result * PRIME + ($typeRoom == null ? 43 : $typeRoom.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    public String toString() {
        return "Restaurant(id=" + this.getId() + ", fullName=" + this.getFullName() + ", mobileNum=" + this.getMobileNum() + ", location=" + this.getLocation() + ", email=" + this.getEmail() + ", imageUrl=" + this.getImageUrl() + ", ratings=" + this.getRatings() + ", user=" + this.getUser() + ", openTime=" + this.getOpenTime() + ", closeTime=" + this.getCloseTime() + ", foodType=" + this.getFoodType() + ", seatNum=" + this.getSeatNum() + ", description=" + this.getDescription() + ", typeRoom=" + this.getTypeRoom() + ", status=" + this.getStatus() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }
}
