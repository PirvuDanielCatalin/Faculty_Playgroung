using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Booktopia.Models
{
    public class BookComment
    {
        [Key]
        public int BookCommentId { get; set; }

        [Required]
        public string Comentariu { get; set; }

        public DateTime DataAprobare { get; set; }

        public int Likes { get; set; }

        public string UserId { get; set; }
        public int BookId { get; set; }

        // Relatii
        public virtual ApplicationUser User { get; set; }
        public virtual Book Book { get; set; }
    }
}