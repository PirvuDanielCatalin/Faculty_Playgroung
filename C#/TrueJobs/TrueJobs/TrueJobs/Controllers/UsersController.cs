using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.IO;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using TrueJobs;

namespace TrueJobs.Controllers
{
    public class UsersController : Controller
    {
        private JobsEntities2 db = new JobsEntities2();

        // GET: Users
        public ActionResult Index()
        {
            return View(db.Users.ToList());
        }

        // GET: Users/Details/5
        public ActionResult Details(/*int? id,*/ string email)
        {
            if (email == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            //User user = db.Users.Find(id);
            User user = db.Users.SingleOrDefault(d => d.Email == email);
            ViewBag.userID = user.User_ID;
            WriteCV wrcv = db.WriteCVs.SingleOrDefault(d=> d.User_ID == user.User_ID);
            ViewBag.writecv = wrcv.WritecvID;
            if (user == null)
            {
                return HttpNotFound();
            }
            return View(user);
        }

        // GET: Users/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Users/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [ValidateInput(false)]
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(HttpPostedFileBase file2, CV cv, IEnumerable<HttpPostedFileBase> files, User user)
        {

            if (file2 != null && file2.ContentLength > 0)
            {
                // extract only the filename
                var fileName = Path.GetFileNameWithoutExtension(file2.FileName) + " _ " + DateTime.Now.ToString("ddHmmss") + Path.GetExtension(file2.FileName);
               
                var path = Path.Combine(Server.MapPath("~/imgpoze"), fileName);
                user.Photo = fileName;

                file2.SaveAs(path);
            }

            if (ModelState.IsValid)
            {
                user.Email = User.Identity.Name.TrimEnd();
                db.Users.Add(user);
                db.SaveChanges();

                foreach (var file in files)
                {
                    if (file != null && file.ContentLength > 0)
                    {
                        var fileName_att = Path.GetFileName(file.FileName);
                        var path = Path.Combine(Server.MapPath("~/App_Data/uploads"), fileName_att);
                        cv.CV_path = fileName_att;
                        cv.User_ID = user.User_ID;
                        file.SaveAs(path);


                        db.CVs.Add(cv);
                        db.SaveChanges();

                    }
                }

                return RedirectToAction("Create2", new { id = user.User_ID});
            }

            return View(user);
        }


        public ActionResult Create2(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            User user = db.Users.Find(id);

            if (user == null)
            {
                return HttpNotFound();
            }

            ViewBag.Tag_ID = new SelectList((from p in db.Interests
                                             select new
                                             {
                                                 Tag_ID1 = p.Interest_ID,
                                                 Tag_name1 = p.Name
                                             }), "Tag_ID1", "Tag_name1");

            return View(user);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create2(User user, Users_interests user_interest)
        {

            if (ModelState.IsValid)
            {
                db.Entry(user).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Success");
            }
            ViewBag.Tag_ID = new SelectList((from p in db.Interests
                                             select new
                                             {
                                                 Tag_ID1 = p.Interest_ID,
                                                 Tag_name1 = p.Name
                                             }), "Tag_ID1", "Tag_name1");
            return View(user);
        }


        public JsonResult AddTag(int id, int elem)
        {
         
            var tag = new Users_interests();

            //set student name

            tag.User_ID = elem;
            tag.Interest_ID = id;


            //create DBContext object
            //Add Student object into Students DBset
            db.Users_interests.Add(tag);

          
            db.SaveChanges();



            return Json(new { success = true }, JsonRequestBehavior.AllowGet);

        }
        //public JsonResult DeleteTag(int id, int elem)
        //{
        //    Articles_tags tagToDelete = new Articles_tags();
        //    tagToDelete.ID_Art = elem;
        //    tagToDelete.Tag_ID = id;
        //    db.Entry(tagToDelete).State = System.Data.Entity.EntityState.Deleted;
        //    db.SaveChanges();

        //    return Json(new { success = true }, JsonRequestBehavior.AllowGet);
        //}
        public JsonResult DeleteTag(int id)
        {
            Interest tag = db.Interests.Find(id);
            Users_interests tagToDelete = db.Users_interests.Where(a => a.Interest_ID == tag.Interest_ID).FirstOrDefault();
            db.Users_interests.Remove(tagToDelete);
            db.SaveChanges();


            return Json(new { success = true }, JsonRequestBehavior.AllowGet);
        }



        [ActionName("userinterest")]
        public ActionResult UserInterest(int id)
        {


            User user = db.Users.Find(id);
            IEnumerable<Interest> model = from p in db.Interests
                                     join pe in db.Users_interests on p.Interest_ID equals pe.Interest_ID
                                     where pe.User_ID == user.User_ID
                                     select p;           
            return View(model);

        }
        [ActionName("userinterest2")]
        public ActionResult UserInterest2(string email)
        {
            User user = db.Users.SingleOrDefault(d=>d.Email==email);
            IEnumerable<Interest> model = from p in db.Interests
                                          join pe in db.Users_interests on p.Interest_ID equals pe.Interest_ID
                                          where pe.User_ID == user.User_ID
                                          select p;
            return View(model);
        }


        public ActionResult Downloads(int id = 0)
        {
            var fileToRetrieve = db.CVs.Where(f => f.User_ID == id);
            return View(fileToRetrieve);
        }

        public FileResult Download(string name)
        {

            var path = "~/App_Data/uploads/" + name;
            return File(path, "application/force-download", Path.GetFileName(path));

        }

        // GET: Users/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            User user = db.Users.Find(id);
            if (user == null)
            {
                return HttpNotFound();
            }
            return View(user);
        }

        // POST: Users/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(/*[Bind(Include = "User_ID,FirstName,LastName,Email,Phone,Photo,Location,Experience")] */User user)
        {
            if (ModelState.IsValid)
            {
                db.Entry(user).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(user);
        }

        // GET: Users/Delete/5
        public ActionResult Delete(/*int? id*/ string email)
        {
            if (email == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            User user = db.Users.SingleOrDefault(d => d.Email == email);
            if (user == null)
            {
                return HttpNotFound();
            }
            return View(user);
        }

        // POST: Users/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(/*int id*/ string email)
        {
            User user = db.Users.SingleOrDefault(d => d.Email == email);

            db.CVs.RemoveRange(db.CVs.Where(c => c.User_ID == user.User_ID));
            db.Users_interests.RemoveRange(db.Users_interests.Where(c=>c.User_ID == user.User_ID));
            db.Users.Remove(user);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
