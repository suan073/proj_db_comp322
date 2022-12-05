WORK entity
WORK 엔티티는 사이트에 관리되어지는 창작물을 나타내는 엔티티이다.
- SSN : 창작물의 식별번호로서 사용되는 속성으로, Key attribute이다.
- WorkTitle : 창작물의 제목 또는 이름을 나타내는 속성이다.
- Media : 창작물이 제작된 매체를 나타내는 속성이다.
- IsAdult : 창작물이 청소년 관람불가 작품인지를 나타내는 정보이다.
- Introduction : 창작물의 줄거리나 개요 등 간단한 설명이 저장되는 속성이다. 
- Language : 창작물이 제작된 언어를 나타내는 속성이다.
- NumOfEpisode : 창작물이 제작된 편수를 나타내는 속성이다.
- Status : 창작물이 제작 전인지, 방영(연재) 중인지, 종료되었는지 등을 나타내는 속성이다.
- Keyword : 창작물의 키워드를 나타내는 속성으로, Multi value Attribute이다.
- Sites : 창작물이 게시된 사이트 이름이나 그 주소가 저장된 속성으로, Multi value Attribute이다.


WRITING_INFO entity
창작물 중 글에 대한 세부정보를 나타내는 엔티티이다.
- WritSSN : 글인 창작물에 부여되는 식별번호로, partial key이다.
- NumOfPages : 창작물의 페이지 수를 나타내는 속성이다.
- Publisher : 창작물의 출판사를 나타내는 속성이다.


VIDEO_INFO entity
영상 창작물에 대한 세부정보를 나타내는 엔티티이다.
- VideoSSN : 영상창작물에 부여되는 식별번호로, partial key이다.
- Country : 영상창작물이 제작된 나라를 표시하는 속성이다.
- ProductionYear : 영상창작물의 제작연도를 나타내는 속성이다.


WEBTOON_INFO entity
웹툰인 창작물에 대한 세부정보를 나타내는 엔티티이다.
- ToonSSN : 각 웹툰에 부여되는 식별번호로, partial key이다.
- IsColored : 채색된 웹툰인지 흑백웹툰인지를 나타내는 속성이다.
- ToonIsFree : 웹툰이 유료인지 무료인지를 나타내는 속성이다.


GENRE entity
GENRE 엔티티는 창작물이 속할 수 있는 장르를 나타내는 속성이다. 
- GenreID : 장르의 아이디를 나타내는 속성으로, Key attribute이다.
- GenreName : 장르의 명칭을 나타내는 속성이다.


CREATOR entity
작품의 창작자를 나타내는 엔티티이다.
- CreatorID : 창작자에게 부여되는 개별 ID로, Key Attribute이다.
- CrName : 창작자의 이름을 나타내는 속성이다.
- CrGender : 창작자의 성별을 나타내는 속성이다.
- Job : 창작자의 직업을 나타내는 속성이다.


USER entity
USER 엔티티는 사이트를 사용하는 고객을 나타내는 엔티티이다.
- UserID : 사용자의 아이디를 나타내는 속성으로, Key attribute이다.
- Password : 사용자의 비밀번호를 나타내는 속성이다.
- UserName : 사용자의 이름을 나타내는 속성이다.
- UserGender : 사용자의 성별을 나타내는 속성이다.
- Birth_date : 사용자의 생년월일을 나타내는 속성이다.


LOG entity
LOG 엔티티는 사용자가 작성하게 되는 게시글을 나타내는 weak entity이다.
- LogID : 게시글마다 부여되는 ID를 의미하며, partial key이다.
- LogTitle : 게시글의 제목을 나타내는 속성이다.
- Public : 게시글의 공개여부를 나타내는 속성이다.
- Likes : 다른 사용자들이 누른 좋아요의 개수를 나타내는 속성이다.
- Content : 게시글의 내용을 나타내는 속성이다.
- LogDate : 게시글을 작성한 날짜를 나타내는 속성이다.
 

COMMENT entity
COMMENT 엔티티는 사이트에 게시된 게시글의 댓글을 나타내는 weak entity이다.
- CommentID : 각 댓글에 부여되는 ID를 의미하며 partial key이다.
- Text : 댓글 내용을 나타내는 속성이다.
- Date : 댓글을 작성한 날짜를 나타내는 속성이다.



BE_WRITEN_IN realationship
entity WRITING_INFO, WORK가 참여하는 1 : 1 realationship이다. 
작품(WORK)이 글로 구성되어 있을 경우, 글 정보(WRITING_INFO)와 작품 (WORK)을 '~로 작문되었다(BE_WRITEN_IN)’에 연결하여, 작품과 그것의 글 관련 정보를 연결될 수 있도록 하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
WORK 중 글 관련 작품과 관련된 WORK만 relationship에 참여한다.
반대로, WRITING_INFO는 모두 relationship에 참여한다. 


BE_EDITED_IN realationship
entity VIDEO_INFO, WORK가 참여하는 1 : 1 realationship이다. 
작품(WORK)이 비디오로 구성되어 있을 경우, 비디오 정보(VIDEO_INFO)와 작품(WORK)을 '~로 편집되었다(BE_EDITED_IN)’에 연결하여, 작품과 그것의 비디오 관련 정보를 연결될 수 있도록 하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
WORK 중 비디오 관련 작품과 관련된 WORK만 relationship에 참여한다.
반대로, VIDEO_INFO는 모두 relationship에 참여한다. 


BE_DRAWN_IN realationship
entity WEBTOON_INFO, WORK가 참여하는 1 : 1 realationship이다. 
작품(WORK)이 웹툰로 구성되어 있을 경우, 웹툰 정보(WEBTOON_INFO)와 작품(WORK)을 '~로 그려졌다(BE_DRAWN_IN)’에 연결하여, 작품과 그것의 웹툰 관련 정보를 연결될 수 있도록 하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
WORK 중 웹툰 관련 작품과 관련된 WORK만 relationship에 참여한다.
반대로, WEBTOON_INFO는 모두 relationship에 참여한다. 


MADE relationship
entitiy CREATOR, WORK가 참여하는 1 : N realationship이다. 
작품(WORK)의 창작자에 대한 정보가 밝혀져 있을 경우, 창작자 정보(CREATOR)와 작품(WORK)을 ‘~가 창작하였다(MADE)’에 연결하여, 작품과 그것의 창작자 관련 정보를 연결하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
WORK 중 창작자 정보가 존재하는 관련된 WORK는 relationship에 참여한다.
반대로, CREATOR는 모두 relationship에 참여한다. 
1 : N realtionship에서 WORK가 1에 해당한다.


HAVE relationship
entitiy GENRE, WORK가 참여하는 M : N realationship이다. 
작품(WORK)의 장르에 대한 정보가 밝혀져 있을 경우, 장르 정보(GENRE)와 작품(WORK)을 ‘~한 장르를 가진다(HAVE)’에 연결하여, 작품과 그것의 장르 관련 정보를 연결하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
WORK 중 장르 정보가 존재하는 관련된 WORK는 relationship에 참여한다.
반대로, GENRE는 모두 relationship에 참여한다. 


BE_CONNECTED relationship
entitiy WORK 2개가 참여하는 1 : 1 recursive realationship(self-referencing relationship)이다. 
작품(WORK)의 시리즈/매체 전환에 대한 정보가 밝혀져 있을 경우, 오리지널 작품 정보(origin WORK)와 해당 작품(WORK)을 ‘~한 작품과 연결된다(BE_CONNECTED)’에 연결하여, 작품과 그것의 시리즈 또는 매체 전환 관련 정보를 연결하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
WORK 중 시리즈/매체 전환 정보가 존재하는 관련된 WORK는 relationship에 참여한다.
시리즈/매체 전환 정보가 존재하는 관련된 WORK의 오리지날 작품의 WORK 또한 relationship에 참여한다. 


POST_FOR relationship
entitiy WORK, LOG가 참여하는 1 : N realationship이다. 
작품(WORK)에 대한 포스팅(LOG)가 존재하는 경우, 해당 포스팅(LOG)과 해당 작품(WORK)을 ‘~한 작품에 대해 포스팅하였다(POST_FOR)’에 연결하여, 작품과 그것의 포스팅 정보를 연결하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
WORK 중 시리즈/매체 전환 정보가 존재하는 관련된 WORK는 relationship에 참여한다.
반대로, LOG는 모두 relationship에 참여한다. 
1 : N realtionship에서 WORK가 1이다.


POST relationship
entitiy USER, LOG가 참여하는 1 : N realationship이다. 
유저(USER)가 작성한 포스팅(LOG)이 존재하는 경우, 해당 포스팅(LOG)과 작성자 유저(USER)을 ‘~한 포스팅을 작성하였다(POST)’에 연결하여, 포스팅 정보와 그것의 작성자 유저를 연결하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
USER 중 포스팅을 작성한 USER는 writer의 역할로 relationship에 참여한다.
반대로, LOG는 모두 relationship에 참여한다. 
1 : N realtionship에서 USER가 1이다.


WRITE_FOR relationship
entitiy COMMENT, LOG가 참여하는 1 : N realationship이다. 
포스팅(LOG)에 대한 코멘트(COMMENT)가 존재하는 경우, 해당 포스팅(LOG)과 해당 포스팅에 달린 코멘트(COMMENT)을 ‘~한 포스팅에 대해 코멘트를 작성하였다(WRITE_FOR)’에 연결하여, 포스팅과 그것의 코멘트를 연결하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
LOG 중 코멘트가 달린 LOG는 relationship에 참여한다.
반대로, COMMENT는 모두 relationship에 참여한다. 
1 : N realtionship에서 LOG가 1이다.


WRITE relationship
entitiy USER, COMMENT가 참여하는 1 : N realationship이다. 
유저(USER)가 작성한 코멘트(COMMENT)이 존재하는 경우, 해당 코멘트(COMMENT)과 작성자 유저(USER)을 ‘~한 포스팅을 작성하였다(WRITE)’에 연결하여, 코멘트 정보와 그것의 작성자 유저를 연결하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
USER 중 코멘트을 작성한 USER는 writer의 역할로 relationship에 참여한다.
반대로, COMMENT는 모두 relationship에 참여한다. 
1 : N realtionship에서 USER가 1이다.


INTERESTED relationship
entitiy GENRE, USER가 참여하는 M: N realationship이다. 
사용자(USER)의 좋아하는 장르에 대한 정보가 밝혀져 있을 경우, 장르 정보(GENRE)와 작품(USER)을 ‘~한 장르를 흥미로워한다(INTERESTED)’에 연결하여, 사용자과 그 사람의 좋아하는 장르 관련 정보를 연결하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
USER 중 좋아하는 장르가 존재하는 관련된 USER는 relationship에 참여한다.
반대로, GENRE는 유저가 좋아한다고 한 것만 relationship에 참여한다. 


BE_CONNECTED relationship
entitiy USER 2개가 참여하는 M : N recursive realationship(self-referencing relationship)이다. 
유저(USER)가 follow하고 싶은 유저에 대한 정보가 밝혀져 있을 경우, follow 대상이 되는 특정 유저(USER)와 follower가 되고 싶은(USER)을 ‘~ 유저를 따른다(FOLLOW)’에 연결하여, 특정 유저와 그 사람을 따르는 유저를 연결하였다. 
따라서, 해당 relationship은 다음의 제약 조건을 가진다.
follow하고 싶은 USER가 있는 USER는 relationship에 참여한다.
FOLLOW의 대상이 되는 USER 또한 relationship에 참여한다.
