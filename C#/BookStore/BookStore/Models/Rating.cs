using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace Booktopia.Models
{
    public class Rating
    {
        [Key]
        public int RatingId { get; set; }

        [Required]
        public float Value { get; set; }

        public string UserId { get; set; }
        public int BookId { get; set; }

        // Relatii
        public virtual ApplicationUser User { get; set; }
        public virtual Book Book { get; set; }
    }
}