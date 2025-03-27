package com.farm;

import java.util.List;
import java.util.Scanner;

public class MainExe {
	TreeDao treeDao = new TreeDao();
	UserDao userDao = new UserDao();
	BoardDao boardDao = new BoardDao();

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		boolean isMainMenu = true;
		TreeDao treeDao = new TreeDao();
		UserDao userDao = new UserDao();
		BoardDao boardDao = new BoardDao();
		String loggedInUserId = null; // 로그인한 사용자의 아이디 저장

		System.out.println("=====================================================");
		System.out.println("주연농원");

		while (true) {
			if (isMainMenu) { // 주 메뉴일 때만 메뉴를 출력
				System.out.println("-----------------------------------------------------");
				System.out.println("[1] 나무 목록 보기");
				System.out.println("[2] 게시판");
				System.out.println("[3] 나무 등록하기");
				System.out.println("[4] 나무 수정하기");
				System.out.println("[5] 나무 삭제하기");
				System.out.println("[6] 회원가입");
				System.out.println("[7] 로그인");
				System.out.println("[9] 종료");
				System.out.println("-----------------------------------------------------");
				System.out.print("메뉴를 선택해주세요>> ");

				int menu;
				try {
					menu = Integer.parseInt(scn.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("숫자를 입력해주세요.");
					continue;
				}

				switch (menu) {
				case 1: // 나무 목록 보기
					isMainMenu = false; // 메뉴 표시 안 함
					int pageSize = 5;
					int currentPage = 1;

					while (true) {
						List<Tree> trees = treeDao.list();

						int totalPosts = trees.size();
						int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

						if (currentPage > totalPages)
							currentPage = totalPages;
						if (currentPage < 1)
							currentPage = 1;

						int start = (currentPage - 1) * pageSize;
						int end = Math.min(start + pageSize, totalPosts);

						System.out.println("-----------------------------------------------------");
						for (int i = start; i < end; i++) {
							System.out.println("[" + trees.get(i).getTree_id() + "] " + trees.get(i).getTree_name());
						}
						System.out.println("-----------------------------------------------------");
						System.out.println("페이지 " + currentPage + "/" + totalPages);
						System.out.println("-----------------------------------------------------");
						System.out.println("상세보기>> (게시판 번호) | 이전페이지>> (P) | 다음페이지>> (N)  | 메뉴로 돌아가기>> (M)");

						String input = scn.nextLine().trim().toUpperCase();

						if (input.equals("P")) {
							if (currentPage > 1) {
								currentPage--;
							} else {
								System.out.println("-----------------------------------------------------");
								System.out.println("이전 페이지가 없습니다.");
							}
							continue;
						} else if (input.equals("N")) {
							if (currentPage < totalPages) {
								currentPage++;
							} else {
								System.out.println("-----------------------------------------------------");
								System.out.println("다음 페이지가 없습니다.");
							}
							continue;
						} else if (input.equals("M")) {
							isMainMenu = true;
							break;
						}

						int select;
						try {
							select = Integer.parseInt(input);
							if (select < 1 || select > trees.size()) {
								System.out.println("올바른 번호를 입력해주세요");
								continue;
							}
						} catch (NumberFormatException e) {
							System.out.println("숫자를 입력해주세요.");
							continue;
						}

						Tree selected = treeDao.getTreeById(trees.get(select - 1).getTree_id());

						if (selected != null) {
							System.out.println("=====================================================");
							System.out.println(selected.getTree_name());
							System.out.println("=====================================================");
							System.out.println("나무명: " + selected.getTree_name());
							System.out.println("가격: " + selected.getPrice());
							System.out.println("설명: " + selected.getDescription());
							System.out.println("-----------------------------------------------------");

							while (true) {
								System.out.println("목록으로>> (Y) | 메뉴로 돌아가기>> (M)");
								String choice = scn.nextLine().trim().toUpperCase();

								if (choice.equals("Y")) {
									break; // 목록을 다시 출력
								} else if (choice.equals("M")) {
									isMainMenu = true; // 주 메뉴로 돌아가기
									break; // 메뉴로 돌아가도록 종료
								} else {
									System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
								}
							}
						} else {
							System.out.println("해당 나무를 찾을 수 없습니다.");
						}

						if (isMainMenu) {
							break; // 주 메뉴로 돌아가면 반복문 종료
						}
					}
					break;
				case 2: // 게시판
					isMainMenu = false;
					while (true) {
						while (true) {
							System.out.println("-----------------------------------------------------");
							System.out.println("[1] 게시판 목록        ");
							System.out.println("[2] 게시글 등록");
							System.out.println("[3] 게시글 수정");
							System.out.println("[4] 게시글 삭제");
							System.out.println("[9] 메뉴로 돌아가기");
							System.out.println("-----------------------------------------------------");
							System.out.print("메뉴를 선택해주세요>> ");

							int boardMenu;
							try {
								boardMenu = Integer.parseInt(scn.nextLine());
							} catch (NumberFormatException e) {
								System.out.println("숫자를 입력해주세요.");
								continue;
							}

							switch (boardMenu) {
							case 1:// 게시판 목록

								pageSize = 5;
								currentPage = 1;

								while (true) {
									List<Board> boards = boardDao.list();
									int totalPosts = boards.size();
									int totalPages = (int) Math.ceil((double) totalPosts / pageSize); // 올림해서 정수로

									if (currentPage > totalPages)
										currentPage = totalPages;
									if (currentPage < 1)
										currentPage = 1;

									int start = (currentPage - 1) * pageSize; // 현재 페이지에서 보여줄 게시글의 시작 인덱스 번호
									int end = Math.min(start + pageSize, totalPosts);// 현재페이지에서 보여줄 게시글의 마지막 인덱스 번호

									// 목록 출력
									System.out.println("-----------------------------------------------------");
									System.out.println("번호   |   제목   |   작성자   |   등록일   |   조회수");
									System.out.println("-----------------------------------------------------");
									for (int i = start; i < end; i++) {
										Board board = boards.get(i);
										System.out.println(board.getPost_id() + "   |   " + board.getTitle() + "   |   "
												+ board.getAuthor() + "   |   " + board.getPost_date() + "   |   "
												+ board.getView_count());
									}
									System.out.println("-----------------------------------------------------");

									System.out.println("상세보기>> (게시판 번호) | 이전페이지>> (P) | 다음페이지>> (N) | 게시판 메뉴로>> (B)");
									String choice = scn.nextLine().trim().toUpperCase();

									if (choice.equals("P")) {
										if (currentPage > 1) {
											currentPage--;
										} else {
											System.out.println("-----------------------------------------------------");
											System.out.println("이전 페이지가 없습니다.");
										}
									} else if (choice.equals("N")) {
										if (currentPage < totalPages) {
											currentPage++;
										} else {
											System.out.println("-----------------------------------------------------");
											System.out.println("다음 페이지가 없습니다.");
										}
									} else if (choice.equals("B")) {
										break;
									} else {
										try {
											int postId = Integer.parseInt(choice);
											boardDao.viewCount(postId);
											Board selected = boardDao.getBoardById(postId);
											if (selected != null) {
												System.out.println(
														"=====================================================");
												System.out.println("번호: " + selected.getPost_id());
												System.out.println("조회수: " + selected.getView_count());
												System.out.println("제목: " + selected.getTitle());
												System.out.println("등록일: " + selected.getPost_date());
												System.out.println("작성자: " + selected.getAuthor());
												System.out.println("내용: " + selected.getContent());
												System.out.println(
														"-----------------------------------------------------");

												while (true) {
													System.out.println("목록으로>> (Y) | 메뉴로 돌아가기>> (M)");
													choice = scn.nextLine().trim().toUpperCase();

													if (choice.equals("Y")) {
														break;
													} else if (choice.equals("M")) {
														isMainMenu = true;
														break;
													} else {
														System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
													}
												}
												if (isMainMenu)
													break;
											} else {
												System.out.println("해당 게시물을 찾을 수 없습니다.");
											}
										} catch (NumberFormatException e) {
											System.out.println("숫자를 입력해주세요.");
										}
									}
									if (isMainMenu)
										break;
								}
								break;
							case 2: // 게시글 등록
								System.out.println("-----------------------------------------------------");
								if (!"admin".equals(loggedInUserId)) {
									System.out.println("관리자만 접근할 수 있는 기능입니다.");
									break;
								}

								System.out.println("게시물 제목을 입력해주세요>> ");
								String title = scn.nextLine();
								System.out.println("게시물 내용을 입력해주세요>> ");
								String content = scn.nextLine();
								System.out.println("작성자를 입력해주세요>> ");
								String author = scn.nextLine();

								Board board = new Board();
								board.setTitle(title);
								board.setContent(content);
								board.setAuthor(author);

								if (boardDao.add(board)) {
									System.out.println("등록이 완료되었습니다.");
								} else {
									System.out.println("등록이 예외처리되었습니다.");
								}
								break;
							case 3: // 게시글 수정
								System.out.println("-----------------------------------------------------");
								if (!"admin".equals(loggedInUserId)) {
									System.out.println("관리자만 접근할 수 있는 기능입니다.");
									break;
								}

								System.out.println("수정할 게시물의 번호를 입력해주세요>> ");
								int num = Integer.parseInt(scn.nextLine());
								System.out.println("수정할 게시물 제목을 입력해주세요>> ");
								title = scn.nextLine();
								System.out.println("수정할 게시물 내용을 입력해주세요>> ");
								content = scn.nextLine();
								System.out.println("수정할 작성자를 입력해주세요>> ");
								author = scn.nextLine();

								board = new Board();
								board.setPost_id(num);
								board.setTitle(title);
								board.setContent(content);
								board.setAuthor(author);

								if (boardDao.edit(board)) {
									System.out.println("수정이 완료되었습니다.");
								} else {
									System.out.println("수정이 예외처리되었습니다.");
								}
								isMainMenu = true;
								break;
							case 4: // 게시글 삭제
								System.out.println("-----------------------------------------------------");
								if (!"admin".equals(loggedInUserId)) {
									System.out.println("관리자만 접근할 수 있는 기능입니다.");
									break;
								}

								int boardId = 0;
								while (true) {
									System.out.println("삭제할 게시물의 번호를 입력해주세요>> ");
									String input = scn.nextLine();

									try {
										boardId = Integer.parseInt(input);
										if (boardId > 0) {
											break;
										} else {
											System.out.println("삭제할 게시물의 번호는 1이상의 숫자를 입력해주세요.");
										}
									} catch (NumberFormatException e) {
										System.out.println("숫자를 입력해주세요.");
									}
								}

								if (boardDao.delete(boardId)) {
									System.out.println("삭제가 완료되었습니다.");
								} else {
									System.out.println("삭제가 예외처리되었습니다.");
								}
								break;
							case 9: // 종료
								isMainMenu = true;
								break;
							default:
								System.out.println("잘못된 입력입니다. 메뉴를 다시 선택해주세요.");
							}
							if (isMainMenu)
								break;
						}
						break;
					}
					break;
				case 3: // 나무 등록
					while (true) {
						System.out.println("-----------------------------------------------------");
						if (!"admin".equals(loggedInUserId)) {
							System.out.println("관리자만 접근할 수 있는 기능입니다.");
							break;
						}

						System.out.println("나무 명을 입력해주세요>> ");
						String name = scn.nextLine();
						System.out.println("나무 가격을 입력해주세요>> ");
						int price = Integer.parseInt(scn.nextLine());
						System.out.println("나무에 대한 설명을 입력해주세요>> ");
						String description = scn.nextLine();

						Tree treeR = new Tree(name, price, description);
						if (treeDao.add(treeR)) {
							System.out.println("등록이 완료되었습니다.");
						} else {
							System.out.println("등록이 예외처리되었습니다.");
						}
						break;
					}
					break;
				case 4: // 나무 수정
					while (true) {
						System.out.println("-----------------------------------------------------");
						if (!"admin".equals(loggedInUserId)) {
							System.out.println("관리자만 접근할 수 있는 기능입니다.");
							break;
						}

						System.out.println("수정할 나무의 id를 입력해주세요>> ");
						int id = Integer.parseInt(scn.nextLine());
						System.out.println("나무 명을 입력해주세요>> ");
						String name = scn.nextLine();
						System.out.println("나무 가격을 입력해주세요>> ");
						int price = Integer.parseInt(scn.nextLine());
						System.out.println("나무에 대한 설명을 입력해주세요>> ");
						String description = scn.nextLine();

						Tree treeE = new Tree();
						treeE.setTree_id(id);
						treeE.setTree_name(name);
						treeE.setPrice(price);
						treeE.setDescription(description);

						if (treeDao.edit(treeE)) {
							System.out.println("수정이 완료되었습니다.");
						} else {
							System.out.println("수정이 예외처리되었습니다.");
						}
						break;
					}
					break;
				case 5: // 나무 삭제
					while (true) {
						System.out.println("-----------------------------------------------------");
						if (!"admin".equals(loggedInUserId)) {
							System.out.println("관리자만 접근할 수 있는 기능입니다.");
							break;
						}

						int id = 0;
						while (true) {
							System.out.println("삭제할 나무의 id를 입력해주세요>> ");
							String input = scn.nextLine();

							try {
								id = Integer.parseInt(input);
								if (id > 0) {
									break;
								} else {
									System.out.println("1 이상의 숫자를 입력해주세요.");
								}
							} catch (NumberFormatException e) {
								System.out.println("숫자를 입력해주세요.");
							}
						}

						if (treeDao.delete(id)) {
							System.out.println("삭제가 완료되었습니다.");
						} else {
							System.out.println("삭제가 예외처리되었습니다.");
						}
						break;
					}
					break;
				case 6: // 회원가입
					System.out.println("아이디를 입력해주세요>> ");
					String userId = scn.nextLine();
					System.out.println("이름을 입력해주세요>> ");
					String userName = scn.nextLine();
					System.out.println("비밀번호를 입력해주세요>> ");
					String userPw = scn.nextLine();

					if (userDao.register(userId, userName, userPw)) {
						System.out.println("회원가입이 완료되었습니다.");
					} else {
						System.out.println("회원가입에 실패했습니다. 아이디 중복 여부를 확인해주세요.");
					}
					break;
				case 7:
					System.out.println("아이디를 입력해주세요>> ");
					String user_id = scn.nextLine();
					System.out.println("비밀번호를 입력해주세요>> ");
					String user_pw = scn.nextLine();
					System.out.println("-----------------------------------------------------");

					if (userDao.login(user_id, user_pw)) {
						loggedInUserId = user_id;
						if (user_id.equals("admin") && user_pw.equals("1234")) {
							System.out.println(user_id + "(관리자)님 환영합니다.");
						} else {
							System.out.println(user_id + "님 환영합니다.");
							break;
						}
					} else {
						System.out.println("로그인에 실패했습니다. 아이디, 비밀번호를 다시 입력해주세요.");
						System.out.println("-----------------------------------------------------");
					}
					break;
				case 9: // 종료
					System.out.println("프로그램을 종료합니다. 이용해주셔서 감사합니다!!!");
					System.out.println("End of program");
					return; // 프로그램 종료
				default:
					System.out.println("잘못된 입력입니다. 메뉴를 다시 선택해주세요.");
				}
			}
		}
	}
}