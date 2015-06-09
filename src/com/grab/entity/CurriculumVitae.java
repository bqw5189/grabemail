package com.grab.entity;

/**
 * 简历信息
 * Created by baiqunwei on 15/6/4.
 */
public class CurriculumVitae {
    /**
     * 简历id
     */
    private String id;
    /**
     * 来源
     */
    private String source;
    /**
     * 应聘职位
     */
    private String applyForPost;
    /**
     * 应聘公司
     */
    private String applyForCompany;
    /**
     * 投递时间
     */
    private String deliverDate;
    /**
     * 匹配度
     */
    private String matchPercentage;
    /**
     * 名字
     */
    private String name;
    /**
     * 工龄
     */
    private String standing;
    /**
     * 性别
     */
    private String gender;
    /**
     * 年龄
     */
    private String age;
    /**
     * 婚姻状况
     */
    private String marryStatus;
    /**
     * 身高
     */
    private String tall;

    /**
     * 居住地
     */
    private String placeOfResidence;
    /**
     * 户口
     */
    private String residenceCards;
    /**
     * 地址
     */
    private String address;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮件
     */
    private String email;
    /**
     * 学历
     */
    private String academic;
    /**
     * 专业
     */
    private String special;
    /**
     * 学校
     */
    private String school;

    public String getApplyForPost() {
        return applyForPost;
    }

    public void setApplyForPost(String applyForPost) {
        this.applyForPost = applyForPost;
    }

    public String getApplyForCompany() {
        return applyForCompany;
    }

    public void setApplyForCompany(String applyForCompany) {
        this.applyForCompany = applyForCompany;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(String matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStanding() {
        return standing;
    }

    public void setStanding(String standing) {
        this.standing = standing;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(String marryStatus) {
        this.marryStatus = marryStatus;
    }

    public String getTall() {
        return tall;
    }

    public void setTall(String tall) {
        this.tall = tall;
    }

    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    public String getResidenceCards() {
        return residenceCards;
    }

    public void setResidenceCards(String residenceCards) {
        this.residenceCards = residenceCards;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAcademic() {
        return academic;
    }

    public void setAcademic(String academic) {
        this.academic = academic;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurriculumVitae that = (CurriculumVitae) o;

        if (!id.equals(that.id)) return false;
        if (!source.equals(that.source)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + source.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CurriculumVitae{" +
                "id='" + id + '\'' +
                ", source='" + source + '\'' +
                ", applyForPost='" + applyForPost + '\'' +
                ", applyForCompany='" + applyForCompany + '\'' +
                ", deliverDate='" + deliverDate + '\'' +
                ", matchPercentage='" + matchPercentage + '\'' +
                ", name='" + name + '\'' +
                ", standing='" + standing + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", marryStatus='" + marryStatus + '\'' +
                ", tall='" + tall + '\'' +
                ", placeOfResidence='" + placeOfResidence + '\'' +
                ", residenceCards='" + residenceCards + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", academic='" + academic + '\'' +
                ", special='" + special + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
