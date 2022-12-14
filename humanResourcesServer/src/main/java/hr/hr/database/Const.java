package hr.hr.database;

// класс Const. Тут хранятся константы для связи с конкретной табличкой, с конкретными полями в этой таблице и тд


public class Const {
    public static final String USER_TABLE="users";              // public - чтобы была везде доступна (из всех классов), static - для того, чтобы обращаться к константе без создания объекта на основе этого класса, final - значит что переменная константная

    public static final String USER_ID="iduser";
    public static final String USER_NAME="name_user";
    public static final String USER_LOGIN="login_user";
    public static final String USER_PASSWORD="password_user";
    public static final String USER_STATUS="statusUser";
}
