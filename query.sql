--Interested
--1
(select g.genreid, g.genrename 
from Genre G, interested I
where G.Genreid = I.Genreid)
minus
(select g.genreid, g.genrename 
from Genre G, interested I
where pjuserid = 'momomo'
and G.Genreid = I.Genreid);
--2
select g.genrename 
from Genre G, interested I
where pjuserid = 'momomo'
and G.Genreid = I.Genreid;
--3
select genreid
from genre
where genrename = 'Comedy';
--4
select * 
from interested 
where pjUserid = 'momomo'
and Genreid = 13;


--FilterInfo
--1
select * from work 
where  (language= 'Korean'  or language= 'ja' )  
and  (status= 'Pause'  or status= 'End'  or status= 'Ongoing' ) ;

--Login
--1
SELECT * from PJUSER where pjuserid='momomo' and password='abcdef';

--MyPage
--1
select pjuserid  from follow where followerid='XoOoOong';
--2
select pjuser.password from pjuser where pjuserid='XoOoOong';
--3
--update pjuser set password=? where pjuserid=?;

--OpenBoard
--1
select * from pjlog;
--2
select pjlogid, writerid, pjlogdate, pjlogtitle, pjcontents 
from pjlog 
where pjpublic='Y' and (pjlogtitle like '%a%' or pjcontents like '%a%' or writerid like '%a%')
order by pjlogdate desc;
--3
select writerid, pjlogdate, pjlogtitle, pjcontents 
from pjlog 
where pjlogid=44;
--4
--insert into pjcomment values (, ?, ?, ?, ?);
--5
--insert into pjlog values (?, ?, ?, ?, ?, ?, ?, ?);

--OtherUser
--1
select * from pjuser where pjuserid='momomo';
--2
select writerid, pjlogdate, pjlogtitle, pjcontents 
from pjlog 
where pjpublic='Y' and writerid='momomo'
order by pjlogdate desc;
--3
--delete from follow where pjuserid=? and followerid=?
--4
--insert into follow values (?, ?)

--Searching
--1
Select distinct language from WORK;
Select distinct isadult from WORK;
Select distinct media from WORK;
Select distinct status from WORK;

--2
Select * from WORK where language='Korean';
Select * from WORK where isadult='Y';
Select * from WORK where media='Drama';
Select * from WORK where status='End';

--3
select W.ssn, Worktitle, sumoflikes 
from ( select * from (
select * from work 
where  (language= 'Korean'  or language= 'ja' )  
and  (status= 'Pause'  or status= 'End'  or status= 'Ongoing' )
) where WORKTITLE like '%a%'
) W, (select Writeabout, sum(likes) as sumoflikes from pjlog
group by Writeabout) O where W.ssn = O.Writeabout order by sumoflikes desc;  

select W.ssn, Worktitle, sumoflikes, crname 
from ( select * from (
select * from work 
where  (language= 'Korean'  or language= 'ja' )  
and  (status= 'Pause'  or status= 'End'  or status= 'Ongoing' )
) W, creator C, made M where W.ssn = M.wssn and M.creatorid = C.creatorid and crname like '%a%'
) W, (select Writeabout, sum(likes) as sumoflikes from pjlog
group by Writeabout) O where W.ssn = O.Writeabout order by sumoflikes desc;  

select W.ssn, Worktitle, sumoflikes, keyword 
from ( select * from (
select * from work 
where  (language= 'Korean'  or language= 'ja' )  
and  (status= 'Pause'  or status= 'End'  or status= 'Ongoing' )
) W, keyword K where W.ssn =  K.wssn and keyword like '%a'
) W, (select Writeabout, sum(likes) as sumoflikes from pjlog
group by Writeabout) O where W.ssn = O.Writeabout order by sumoflikes desc;  

--4
select * from work N
        left outer join
        (select ssn, worktitle as origintitle from work) O
        on N.originssn = O.ssn
where N.ssn = 021;

--TimeLine
--1
(select writerid, pjlogtitle, pjcontents, pjlogdate, pjlogid
from pjlog
where pjpublic='Y' and writerid in (select pjuserid
                from follow
                where followerid='XoOoOong'))
union
(select writerid, pjlogtitle, pjcontents, pjlogdate, pjlogid
from pjlog L, have H
where pjpublic='Y' and L.writeabout = H.wssn
and H.genreid in (select I.genreid 
                from interested I
                where I.pjuserid = 'momomo')) 
order by pjlogdate desc;
