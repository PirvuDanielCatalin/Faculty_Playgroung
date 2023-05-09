using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace Booktopia.Models
{
    public class BookCategory
    {
        [Key]
        public int BookCategoryId { get; set; }

        public int BookId { get; set; }
        public int CategoryId { get; set; }

        // Relatii 
        public virtual Book Book { get; set; }
        public virtual Category Category { get; set; }

    }
}