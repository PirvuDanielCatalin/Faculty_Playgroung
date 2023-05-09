using Booktopia.Models;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Booktopia.Controllers
{
    [Authorize(Roles = "Administrator")]
    public class UsersController : Controller
    {
        private ApplicationDbContext db = ApplicationDbContext.Create();

        public ActionResult Index()
        {
            ViewBag.Users = db.Users.OrderBy(u => u.UserName);
            ViewBag.Roles = GetAllRoles();
            foreach (var elem in db.Users.OrderBy(u => u.UserName).ToList())
            {
                var y = elem.Roles.FirstOrDefault().RoleId;
            }
            return View();
        }

        [NonAction]
        public IEnumerable<SelectListItem> GetAllRoles()
        {
            var selectList = new List<SelectListItem>();
            var roles = from role in db.Roles select role;
            foreach (var role in roles)
            {
                selectList.Add(new SelectListItem
                {
                    Value = role.Id.ToString(),
                    Text = role.Name.ToString()
                });
            }
            return selectList;
        }

        [HttpPost]
        public ActionResult UpdateUsersRoles(string data)
        {
            var values = JsonConvert.DeserializeObject<Dictionary<string, string>>(data);
            var succes = true;
            foreach (KeyValuePair<string, string> keyvalue in values)
            {
                succes = succes && this.Edit(keyvalue.Key, keyvalue.Value);
            }
            return (succes) ? Content("Succes") : Content("Error");
        }

        [NonAction]
        public bool Edit(string userId, string roleId)
        {
            ApplicationUser user = db.Users.Find(userId);
            var userRole = user.Roles.FirstOrDefault();
            var Role = db.Roles.Find(roleId);
            try
            {
                ApplicationDbContext context = new ApplicationDbContext();
                var roleManager = new RoleManager<IdentityRole>(new RoleStore<IdentityRole>(context));
                var UserManager = new UserManager<ApplicationUser>(new UserStore<ApplicationUser>(context));
                if (TryUpdateModel(user))
                {
                    var roles = from role in db.Roles select role;
                    foreach (var role in roles)
                    {
                        UserManager.RemoveFromRole(userId, role.Name);
                    }
                    UserManager.AddToRole(userId, Role.Name);
                    db.SaveChanges();
                }
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }
    }
}