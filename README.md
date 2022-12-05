\# proj_db_comp322
1. 로그인 
> 아이디와 비밀 번호를 입력
> 해당 아이디와 비밀번호가 DB의 유저 정보로 등록되어 있다면 로그인 성공, 메뉴 페이지로 이동
> 하나라도 틀릴 시 로그인 실패가 뜨면서 로그인 페이지 유지

2. 메뉴
- 1. 작품 탐색창
    * 검색에 사용할 ‘언어’, ’연령 제한’, ’매체’, ‘상태’에 대해 필터를 걸 수 있음
        - “필터를 건다”는 것은 체크박스로 선택한 작품 중에서 검색을 진행한다는 것임
        검색할 범위를 무조건 선택하고, 검색어를 입력해야 함.
        필터로 거른 작품들 중 검색할 범위에 해당 검색어가 포함된 작품들을 보여줌
    * 검색 범위에 따라 표시되는 결과
        - 제목 내 검색 : 해당 작품의 제목
        창작자명 내 검색 : 창작자명-제목
        키워드명 내 검색 : 키워드명-제목
    * 결과의 정렬 순서
        사람들의 관심이 높은 작품부터 보여주기 위해 해당 작품에 대한 게시글들의 좋아요 수를 다합하여 그 총합이 높은 작품이 먼저 보이도록 정렬함
- 2. 게시판
    * 작품에 대한 여러 게시글을 보여주는 곳
    * 게시글은 최신순으로 정렬되어 보여짐
    * 게시판에서 특정 작품과 관련된 글을 작성할 수 있음
        - 작품 정보를 설정하지 않아도 글 작성은 가능함
    * 검색어 입력을 통해 게시글의 제목 또는 내용에 검색어가 포함된 글을 찾을 수 있음
        - 검색 결과로 게시글들이 나열됨
        게시글에는 작성자, 제목, 글, 날짜, 댓글 수, 좋아요 수가 보임
        댓글 수 왼쪽의 '댓글' 버튼을 누르면 해당 작품의 댓글이 보임
            - 여기서 댓글을 추가로 입력할 수 있음
        - '해당 글쓴이가 쓴 모든 글을 확인'하는 버튼을 누르면 해당 글쓴이가 작성한 모든 게시글이 보임 
            - 여기서 해당 글쓴이를 '팔로우' 할 수 있음 
- 3. 마이페이지
    * 본인이 팔로우한 유저들이 나열된 것을 볼 수 있음
        - 팔로우한 유저는 언팔로우를 통해 팔로우 삭제를 할 수 있음
        팔로우 유저의 아이디 옆의 버튼을 통해 해당 유저의 모든 글을 확인할 수 있음
    * 본인의 선호장르를 확인 할 수 있음
        - 이때, 표에 모든 장르가 나오고 선호장르에 체크가 되어 있는데, 해당 체크박스의 선택 여부를 수정하고 적용하여 선호 장르를 재설정할 수 있음 
- 4. 타임 라인
    * 본인이 팔로우한 팔로워나 선호 장르의 작품에 해당하는 게시글이 최신순으로 정렬되어 보여짐
    * 게시판에서 검색과 글작성 기능을 제외하고 동일한 기능을 제공함


