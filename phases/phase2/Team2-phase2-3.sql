-- type 1
--1)
select crname
from creator
where job='Illustrator';

--2)
select writerid
from pjcomment
where targetposting=0;

--3)
select worktitle, media, language, numofepisode
from work
where isadult='N' and numofepisode<50;

--4)
select likes, writerid
from pjlog
where pjlogtitle='기대';

-- type 2
--1)
select c.crname, w.worktitle
from work w, made m, creator c
where w.ssn=m.wssn and c.creatorid=m.creatorid;

--2)
select w.worktitle
from work w, writing_info b
where w.ssn=b.wssn and publisher='Harper Collins';

--3)
select w.worktitle, v.productionyear
from work w, video_info v
where w.ssn=v.wssn and country='United Kingdom';

--4)
select u.pjuserid, genrename
from pjuser u, interested k, genre g
where u.pjuserid=k.pjuserid and g.genreid=k.genreid;

-- type 3
select
    W.MEDIA,
    count(W.SSN)
from
    WORK W,
    HAVE H,
    GENRE G
where
    W.SSN = H.WSSN
    and H.GENREID = G.GENREID
    and G.GENRENAME = 'Romance'
group by W.MEDIA;


-- type 4
select
    count(SSN)
from
    WORK
where
    SSN in (
        select
            W.ssn
        from  
            pjLOG L,
            PJUSER U,
            WORK W
        where
            L.writerid = U.pjuserid
            and L.writeabout = W.ssn
            and U.pjusergender = 'F'
    );
    

    
-- type 5
--1)
select pjuserid, pjusergender
from pjuser u
where not exists ( select *
	from follow f
	where u.pjuserid=f.followerid
);

--2)
select pjuserid, pjusergender
from pjuser u
where exists ( select *
	from interested i, genre g);
	
-- type 6
--1)
select worktitle, media, status, introduction
from work
where ssn in (select wssn
	from genre g, have h
	where g.genreid=h.genreid and genrename='Romance');

--2)
select worktitle, isadult, introduction
from work
where ssn in (select wssn
	from webtoon_info
	where iscolored='N');
-- type 7 
select 
    work_name, 
    recommender
from (
    select 
        worktitle as work_name,
        L.writerid as recommender,
        L.likes as likes
	from 
        PJLOG L,
        WORK W
	where 
        L.pjlogtitle = '추천'
        and L.writeabout = W.ssn
    )
order by likes;

-- type 8
--1)
select worktitle, numofepisode, introduction
from work, webtoon_info
where media='Toon' 
    and ssn=wssn 
    and status='Ongoing' 
    and toonisfree='Y'
order by numofepisode;

--2)
select pjuserid, pjusergender, likes
from pjuser, pjlog
where pjuserid = writerid
and likes > 1000
order by likes desc;

-- type 9
--1)
select productionyear, count(*) as count
from work, video_info
where media='Drama' and ssn=wssn
group by productionyear
order by productionyear;

-- type 10
--1)
select * from
((select pjuserid, pjusername
from pjuser
where pjusergender = 'M')
union
(select pjuserid, pjusername
from pjuser
where password = 'password'));

--2)
select * from
((select WSSN, WRITSSN
from writing_info
where publisher = 'Penguin')
union
(select WSSN, WRITSSN
from writing_info
where publisher = 'Currency'));

