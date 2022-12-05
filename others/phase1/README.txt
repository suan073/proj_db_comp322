readme

팀명 : 꼬꼬D
2020111275 이수인
2020111565 최혜림

WORK entity
WORK 엔티티는 사이트에 관리되어지는 창작물을 나타내는 엔티티이다.
- SSN : 창작물의 식별번호로서 사용되는 속성으로, Key attribute이다.
- Title : 창작물의 제목 또는 이름을 나타내는 속성이다.
- Type : 창작물이 제작된 매체를 나타내는 속성이다.
- Sites : 창작물이 게시된 사이트의 주소가 저장된 속성으로, Multi value Attribute이다.
- Start_date : 창작물이 출시된 날을 나타내는 속성이다.
- Finish_date : 창작물이 드라마 등과 같이 한번에 출시되는 것이 아닌 특정 기간동안 나눠서 출시될 경우에 마지막으로 출시된 날의 정보를 나타내는 정보이다. 
- Age_limit : 창작물의 연령제한을 나타내는 정보이다.
- Description : 창작물의 줄거리나 개요 등 간단한 설명이 저장되는 속성이다. 
- Country : 창작물이 제작된 나라를 표시하는 속성이다.
- Language : 창작물 제작에 주가되는 언어를 나타내는 속성이다.
- ViewCount : 창작물이 관람된 총 횟수를 나타내는 속성이다.

GENRE entity
GENRE 엔티티는 창작물(작품)이 속하는 각 장르를 나타내는 속성이다. 
- TotalWorkNum : 해당 장르를 가진 작품의 총 개수를 나타내는 속성이다.
- GenreID : 장르의 아이디를 나타내는 속성으로, Key attribute이다.
- Name : 장르의 명칭을 나타내는 속성이다.

KEYWORD entity
KEYWORD 엔티티는 창작물(작품)이 가진 키워드를 나타내는 속성이다. 
- TotalWorkNum : 해당 키워드를 가진 작품의 총 개수를 나타내는 속성이다.
- KeywordID : 키워드의 아이디를 나타내는 속성으로, Key attribute이다.
- Name : 키워드의 명칭을 나타내는 속성이다.

CREATOR entity
작품의 창작자를 나타내는 엔티티이다.
- CreatorID : 창작자에게 부여되는 개별 ID로, Key Attribute이다.
- Name : 창작자의 이름을 나타내는 속성이다.
- Gender : 창작자의 성별을 나타내는 속성이다.
- Birth_date : 창작자의 생년월일을 나타내는 속성이다.
- Death_date : 창작자의 사망일을 나타내는 속성이다.
- Job : 창작자의 직업을 나타내는 속성이다.


USER entity
USER 엔티티는 사이트를 사용하는 고객을 나타내는 엔티티이다.
- UserID : 사용자의 아이디를 나타내는 속성으로, Key attribute이다.
- Password : 사용자의 비밀번호를 나타내는 속성이다.
- Name : 사용자의 이름을 나타내는 속성이다.
- Gender : 사용자의 성별을 나타내는 속성이다.
- Birth_date : 사용자의 생년월일을 나타내는 속성이다.
- Fav_type : 좋아하는 타입을 나타내는 속성으로, Multi value Attribute이다.
- Fav_genre : 좋아하는 장르를 나타내는 속성으로, Multi value Attribute이다. 

LOG entity
LOG 엔티티는 사용자가 작성하게 되는 게시글을 나타내는 weak entity이다.
- LogID : 게시글마다 부여되는 ID를 의미하며, partial key이다.
- Title : 게시글의 제목을 나타내는 속성이다.
- Public : 게시글의 공개여부를 나타내는 속성이다.
- Likes : 다른 사용자들이 누른 좋아요의 개수를 나타내는 속성이다.
- Content : 게시글의 내용을 나타내는 속성이다.
- Date : 게시글을 작성한 날짜를 나타내는 속성이다.
 
COMMENT entity
COMMENT 엔티티는 사이트에 게시된 게시글의 댓글을 나타내는 weak entity이다.
- CommentID : 각 댓글에 부여되는 ID를 의미하며 partial key이다.
- Text : 댓글 내용을 나타내는 속성이다.
- Date : 댓글을 작성한 날짜를 나타내는 속성이다.

BELONG TO relationship
WORK와 GENRE 엔티티가 참여하는 1:N binary realtionship이다. GENRE는 최대 N개의 WORK가 속해질 수 있고, 소속된 WORK가 존재하지 않을 수도 있다. WORK는 반드시 1개의 GENRE가 필요하다.

HAVE relationship
WORK와 KEYWORD 엔티티가 참여하는 M:N binary realtionship이다. WORK는 최대 N개의 KEYWORD를 가질 수 있고, KEYWORD를 가지지 않을 수도 있다. KEYWORD는 반드시 1개 이상의 WORK가 필요하다.

MADE relationship
WORK와 CREATOR 엔티티가 참여하는 M:N binary realtionship이다. CREATOR는 최대 1개 이상의 WORK를 제작해야하며, 한 작품의 제작에는 여러명의 CREATOR가 참여할 수 있다. WORK는 반드시 1명 이상의 CREATOR가 필요하다.
 
FOLLOW relationship
USER간에 가질 수 있는 M:N binary realtionship이다. USER는 최대 N개의 다른 USER를 가질 수 있고, 다른 USER를 가지지 않을 수도 있다.
 
POST relationship
USER와 LOG 엔티티가 참여하는 1:N binary realtionship이다. USER는 최대 N개의 LOG를 작성할 수 있고, LOG를 작성하지 않을 수도 있다. LOG는 반드시 1개의 USER가 필요하다.
 
WRITE relationship
USER와 COMMENT 엔티티가 참여하는 1:N binary realtionship이다. USER는 최대 N개의 COMMENT를 작성할 수 있고, COMMENT를 작성하지 않을 수도 있다. LOG는 반드시 1개의 USER가 필요하다.
 
WRITE FOR relationship
LOG와 COMMENT 엔티티가 참여하는 1:N binary realtionship이다. LOG는 최대 N개의 COMMENT를 가질 수 있고, COMMENT를 가지지 않을 수도 있다. COMMENT는 반드시 1개의 LOG가 필요하다.
 
POST FOR relationship
WORK와 LOG 엔티티가 참여하는 1:N binary realtionship이다. WORK는 최대 N개의 LOG를 가질 수 있고, LOG를 가지지 않을 수도 있다. LOG는 반드시 1개의 WORK가 필요하다.
 
BE CONNECTED relationship
WORK와 WORK 엔티티가 참여하는 1:N binary realtionship이다. WORK는 원작 작품으로 관계에 참여하는 경우 origin의 role을 가지며, 원작과 연관된 작품으로 관계에 참여하는 경우 work의 role을 가진다. origin은 최대 N개의 work를 가질 수 있다. work로 관계에 참여하는 경우 반드시 1개의 origin을 가진다.
- ConnectType : origin 작품과 work 작품이 어떤식으로 연관되는지 (영화화, 드라마화, 시리즈인지 등의) 연관성의 명칭을 나타내는 속성이다.
- SeriesNo : 작품이 해당 시리즈의 몇번째로 제작된 시리즈인지 나타내는 속성이다. 
