package aquan.online.shortlink.admin.test;


public class UserShardingTableTest {

    /**
     * 用于生成分表的sql代码
     *
     * @param args
     */
    public static void main(String[] args) {
        String sql ="create table shortlink.t_link_goto_%d\n" +
                    "(\n" +
                    "    id        bigint      not null comment 'ID'\n" +
                    "        primary key auto_increment,\n" +
                    "    full_short_url varchar(128)  null,\n" +
                    "    gid       varchar(32) null\n" +
                    ");";
        for (int i = 0; i < 16; i++) {
            System.out.printf((sql) + "%n", i);
        }
    }

}
