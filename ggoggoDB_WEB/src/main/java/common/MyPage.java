package common;

import java.sql.*; // import JDBC package
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyPage {
	private String userid;
	private Connection conn;

	public MyPage(Connection conn, String userid) {
		this.userid = userid;
		this.conn = conn;
	}

	void executeMyPage(Scanner scanner) {
		while (true) {
			String choice;

			System.out.println();
			System.out.println("*** 마이페이지 ***");
			System.out.println("1. 비밀번호 변경");
			System.out.println("2. 내 팔로우 목록");
			System.out.println("0. 돌아가기");

			choice = scanner.nextLine();

			if (choice.equals("1")) {
				updatePassword(scanner);

			} else if (choice.equals("2")) {
				while (true) {

					System.out.println("게시물을 보고싶은 사용자의 ID를 입력하세요");
					System.out.print("뒤로 가려면 'n'을 입력하세요: ");
					String targetId = scanner.nextLine();

					if (targetId.equals("n"))
						break;

					if (!infollowing(targetId))
						System.out.println("잘못된 입력입니다.");
					else {
						OtherUser targetUser = new OtherUser(targetId);
						targetUser.showUserLog(conn);

						System.out.print("이 사용자를 언팔로우 할까요? (y/n): ");
						choice = scanner.nextLine();

						if (choice.equals("y")) {
							targetUser.unfollow(conn, userid);
						} else if (choice.equals("n")) {
							System.out.println("목록으로 돌아갑니다.");
						} else {
							System.out.println("잘못된 입력입니다.");
						}
					}

				}
			} else if (choice.equals("0")) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
				continue;
			}
		}
	}
	
	// new method in Ph4
	public List<Log> showUserLog(){
		List<Log> logs = new ArrayList<Log>();
		try {
			String sql = "select * from pjlog left outer join (select targetposting, count (*) as commentnum\r\n"
					+ "from pjcomment group by targetposting) on pjlogid=targetposting\r\n"
					+ "where writerid=? order by pjlogdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userid);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Log log = new Log(rs);
				logs.add(log);
			}
			ps.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return logs;
	}

	boolean infollowing(String oUserId) {
		List<String> following = getFollowing();
		for (String f : following)
			if (oUserId.equals(f))
				return true;
		return false;
	}
	
	// new method in Ph4
	public List<String> getFollowing() {
		List<String> following = new ArrayList<String>();
		try {
			String sql = "select pjuserid from follow where followerid=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userid);
			ResultSet rs = ps.executeQuery();

			while (rs.next())
				following.add(rs.getString(1));

			ps.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		return following;
	}

//	void printFollowing() {
//		List<String> following = getFollowing();
//		System.out.println();
//		System.out.println("* 팔로잉 목록 *");
//		System.out.println("--------------------");
//		for (String fuser : following)
//			System.out.println(fuser);
//		System.out.println();
//	}

	void updatePassword(Scanner scan) {
		try {
			System.out.print("기존 비밀번호를 입력하세요: ");
			String pw = scan.nextLine();

			String sql = "select pjuser.password from pjuser where pjuserid=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userid);
			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				System.out.print("오류!");
			else {
				if (rs.getString(1).equals(pw)) {
					System.out.print("새 비밀번호를 입력하세요: ");
					String newPw = scan.nextLine();

					sql = "update pjuser set password=? where pjuserid=?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, newPw);
					ps.setString(2, userid);

					int res = ps.executeUpdate();

					if (res == 1){
						System.out.println("비밀번호가 변경되었습니다.");
						conn.commit();
					}
					else
						System.out.println("오류!");
				} else
					System.out.println("잘못된 비밀번호입니다.");
			}

			ps.close();
			rs.close();
		} catch (SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
}
