--suin
select * from Interested
where pjuserid = 'momomo';

select g.genrename 
from Genre G, interested I
where pjuserid = 'momomo'
and G.Genreid = I.Genreid;


(select g.genreid, g.genrename 
from Genre G, interested I
where G.Genreid = I.Genreid)
minus
(select g.genreid, g.genrename 
from Genre G, interested I
where pjuserid = 'momomo'
and G.Genreid = I.Genreid);

select genreid
from genre
where genrename = 'Comedy';

insert into interested values('momomo',13);
rollback;

Insert into INTERESTED (PJUSERID,GENREID) values ('momomo',13);
delete from INTERESTED where PJUSERID='momomo' and GENREID=13;

select * 
from interested 
where pjUserid = 'momomo'
and Genreid = 13;

SELECT * from PJUSER where pjuserid='momomo' and password='abcdef';

Select distinct language from work;

select * from work;

select * from (select * from work)
where WORKTITLE like '%Flower%';

select * from (select * from work) W, creator C, made M
where W.ssn = M.wssn
and M.wssn = C.creatorid
and crname like '%Lee%';

select * from (select * from work) W, keyword K
where W.ssn =  K.wssn
and keyword like '%M%';

select * from work 
where language = 'Korean'
or language =  'ja'
or isadult = 'Y';

select * from work 
where  (language= 'Korean'  or language= 'ja' )  
and  (status= 'Pause'  or status= 'End'  or status= 'Ongoing' ) ;

select Writeabout, sum(likes) as sumoflikes
from pjlog
group by Writeabout;

select W.ssn, Worktitle, sumoflikes
from
(select * from work 
where  (language= 'Korean'  or language= 'ja' )  
and  (status= 'Pause'  or status= 'End'  or status= 'Ongoing' )) W,
(select Writeabout, sum(likes) as sumoflikes
from pjlog
group by Writeabout) O
where W.ssn = O.Writeabout;


select W.ssn, Worktitle, sumoflikes
from
(select * from work 
where  (language= 'Korean'  or language= 'ja' )  
and  (status= 'Pause'  or status= 'End'  or status= 'Ongoing' )) W,
(select Writeabout, sum(likes) as sumoflikes
from pjlog
group by Writeabout) O
where W.ssn = O.Writeabout
order by sumoflikes desc;

--setInterestedGenre
--1. read interested genre list
select 