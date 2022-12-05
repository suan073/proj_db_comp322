--InterestedGenre
select g.genreid, g.genrename, i.pjuserid
from Genre g
left outer join 
    (select *
    from interested
    where pjuserid = 'momomo') i
on g.Genreid = i.Genreid ;

--Login
--1
SELECT * from PJUSER where pjuserid='momomo' and password='abcdef';

--Search
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

--4
select W.ssn, Worktitle, sumoflikes, crname 
from ( select * from (
select * from work 
where  (language= 'Korean'  or language= 'ja' )  
and  (status= 'Pause'  or status= 'End'  or status= 'Ongoing' )
) W, creator C, made M where W.ssn = M.wssn and M.creatorid = C.creatorid and crname like '%a%'
) W, (select Writeabout, sum(likes) as sumoflikes from pjlog
group by Writeabout) O where W.ssn = O.Writeabout order by sumoflikes desc;  

--5
select W.ssn, Worktitle, sumoflikes, keyword 
from ( select * from (
select * from work 
where  (language= 'Korean'  or language= 'ja' )  
and  (status= 'Pause'  or status= 'End'  or status= 'Ongoing' )
) W, keyword K where W.ssn =  K.wssn and keyword like '%a'
) W, (select Writeabout, sum(likes) as sumoflikes from pjlog
group by Writeabout) O where W.ssn = O.Writeabout order by sumoflikes desc;  

--6
select * from work N
        left outer join
        (select ssn, worktitle as origintitle from work) O
        on N.originssn = O.ssn
where N.ssn = 021;

--TimeLine
--1
select * from ((select *
from pjlog
where pjpublic='Y' and writerid in (select pjuserid
                from follow
                where followerid='momomo'))
union
(select L.*
from pjlog L, have H
where pjpublic='Y' and L.writeabout = H.wssn
and H.genreid in (select I.genreid 
                from interested I
                where I.pjuserid = 'momomo'))) 
order by pjlogdate desc;
