package yidan.ambassador.constant;

import java.security.PublicKey;

/**
 * Created by xyd on 16/4/7.
 */
public abstract class SQLCommand {
    public static String ADMIN_USERID = "select Admin_ID from Administrator";
    public static String ADMIN_PASS = "select Admin_Password from Administrator";
    public static String USER_EMAIL = "select Amb_ID,Amb_Email,_id from Ambassador";
    public static String USER_PASS = "select Amb_Password from Ambassador";
    public static String EVENT_LIST = "select Event_Num,Event_Date," +
            "Event_Time,Event_Duration,Event_Topic,Event_Desc,Event_Format,Event_AmbAmount,_id " +
            "from Event";
    public static String EVENT_EDIT = "select Event_Num,Event_Date," +
            "Event_Time,Event_Duration,Event_Topic,Event_Desc,Event_Format,Event_AmbAmount,_id " +
            "from Event where Event_Num = ?";
    public static String FUTURE_EVENT_LIST = "select Event_Num,Event_Date," +
            "Event_Time,Event_Duration,Event_Topic,Event_Desc,Event_Format,Event_AmbAmount,_id " +
            "from Event where Event_Date > '2016-04-22' order by Event_Date asc";
    public static String AMB_LIST = "select Ambassador.Amb_ID, Amb_Name, Amb_Citizenship," +
            "Major.Major_Num,Major_Name, Amb_GradYr, Amb_WorkHr, Amb_Phone, Amb_Email, Amb_WorkExp, Ambassador._id, Major._id " +
            "from Ambassador,Major where Ambassador.Major_Num = Major.Major_Num ";
    public static String AMB_ORDER_LIST = "select Ambassador.Amb_ID,Amb_Name,Amb_Citizenship,Major.Major_Num,Amb_GradYr,Amb_WorkHr," +
            "Amb_Phone,Amb_Email,Amb_WorkExp,Amb_Password,Major_Name,Ambassador._id,Major._id from Ambassador, Major " +
            "where Ambassador.Major_Num = Major.Major_Num order by Major_Name,Ambassador._id,Major._id,Amb_Name";
    public static String AMB_SKILL_LIST = "select Amb_ID, Skill_Num, _id from AmbassadorSkill";
    public static String AMB_PERFORMANCE = "select Amb_ID, Event_Num, Admin_ID, " +
            "Criteria_Num, Perform_Score, _id from Performance";
    public static String AMB_REGISTER = "select Major_Name,Amb_Name,Register._id,Ambassador._id,Major._id from Ambassador,Register,Major " +
            "where Ambassador.Amb_ID = Register.Amb_ID and " +
            "Ambassador.Major_Num = Major.Major_Num and Event_Num = ? " +
            "group by Major_Name,Amb_Name,Register._id";
    public static String AMB_REGISTER_TOTAL = "select count(Amb_ID) from Register where Event_Num = ?";
    public static String INSERT_CRITERIA1 = "insert into Performance(Amb_ID,Event_Num,Admin_ID,Criteria_Num," +
            "Perform_Score,_id) values (?,?,?,?,?,?)";
    public static String PERFORMANCE_MAX = "select max(_id) from Performance";
    public static String PERFORMANCE_COUNT = "select count(*) as _id from Performance";
    public static String USER_REGISTER = "insert into Register(Amb_ID,Event_Num," +
            "Register_Date,_id) values (?,?,?,?)";
    public static String USER_EVENT_SELECT = "select count(*) from Register where Amb_ID = ? and Event_Num = ? ";
    public static String REGISTER_MAX =  "select max(_id) from Register";
    public static String REGISTER_COUNT = "select count(*) from Register";
    public static String PROFILE_AMB = "select Amb_Name,Amb_Citizenship,Amb_GradYr," +
            "Major_Name,Amb_Phone,Amb_WorkHr,Amb_WorkExp,Amb_Email from Ambassador,Major " +
            "where Ambassador.Major_Num = Major.Major_Num and Amb_ID = ?";
    public static String AMB_PROFILE_AVG = "select Amb_ID, Event_Num, Admin_ID, " +
            "Criteria_Num, Perform_Score, _id from Performance where Amb_ID = ?";
    public static String ADMIN_ADDNEWEVENT = "insert into Event values (?,?,?,?,?,?,?,?,?)";
    public static String USER_MAJOR = "select Major_Name as _id from Major";
    public static String USER_SIGNUP = "insert into Ambassador(Amb_ID, Amb_Name, " +
            "Amb_Citizenship, Major_Num, Amb_GradYr, Amb_WorkHr, Amb_Phone, " +
            "Amb_Email, Amb_WorkExp, Amb_Password,_id) values (?,?,?,?,?,?,?,?,?,?,?)";
    public static String ADMIN_CODE = "select Administrator.Admin_ID from Administrator";
    public static String USER_MYEVENT = "select Register.Event_Num,Event_Date," +
            "Event_Time,Event_Duration,Event_Topic,Event_Desc,Event_Format,Event_AmbAmount,Register._id as _id " +
            "from Event,Register where Event.Event_Num = Register.Event_Num and " +
            "Event_Date > '2016-04-22' and Register.Amb_ID = ? " +
            "order by Event_Date asc";
}
