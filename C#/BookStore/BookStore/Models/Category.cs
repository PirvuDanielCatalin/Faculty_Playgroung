using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Booktopia.Models
{
    public class Category
    {
        [Key]
        public int CategoryId { get; set; }

        [Required]
        public string Nume { get; set; }

        // Relatii
        public virtual ICollection<BookCategory> BookCategories { get; set; }
    }
}