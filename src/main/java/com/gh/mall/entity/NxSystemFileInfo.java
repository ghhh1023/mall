package com.gh.mall.entity;

public class NxSystemFileInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nx_system_file_info.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nx_system_file_info.originName
     *
     * @mbggenerated
     */
    private String originname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column nx_system_file_info.fileName
     *
     * @mbggenerated
     */
    private String filename;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nx_system_file_info.id
     *
     * @return the value of nx_system_file_info.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nx_system_file_info.id
     *
     * @param id the value for nx_system_file_info.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nx_system_file_info.originName
     *
     * @return the value of nx_system_file_info.originName
     *
     * @mbggenerated
     */
    public String getOriginname() {
        return originname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nx_system_file_info.originName
     *
     * @param originname the value for nx_system_file_info.originName
     *
     * @mbggenerated
     */
    public void setOriginname(String originname) {
        this.originname = originname == null ? null : originname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column nx_system_file_info.fileName
     *
     * @return the value of nx_system_file_info.fileName
     *
     * @mbggenerated
     */
    public String getFilename() {
        return filename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column nx_system_file_info.fileName
     *
     * @param filename the value for nx_system_file_info.fileName
     *
     * @mbggenerated
     */
    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }
}