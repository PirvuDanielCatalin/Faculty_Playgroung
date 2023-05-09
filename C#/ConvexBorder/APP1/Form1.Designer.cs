namespace APP1
{
    partial class Window
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.Meniu = new System.Windows.Forms.Panel();
            this.Desen = new System.Windows.Forms.CheckBox();
            this.FrontInf = new System.Windows.Forms.CheckBox();
            this.FrontSup = new System.Windows.Forms.CheckBox();
            this.Result = new System.Windows.Forms.Button();
            this.Clear = new System.Windows.Forms.Button();
            this.Apropiat = new System.Windows.Forms.CheckBox();
            this.Citire = new System.Windows.Forms.CheckBox();
            this.canvas = new System.Windows.Forms.Panel();
            this.Comentarii = new System.Windows.Forms.ListBox();
            this.Meniu.SuspendLayout();
            this.SuspendLayout();
            // 
            // Meniu
            // 
            this.Meniu.BackColor = System.Drawing.Color.DimGray;
            this.Meniu.Controls.Add(this.Desen);
            this.Meniu.Controls.Add(this.FrontInf);
            this.Meniu.Controls.Add(this.FrontSup);
            this.Meniu.Controls.Add(this.Result);
            this.Meniu.Controls.Add(this.Clear);
            this.Meniu.Controls.Add(this.Apropiat);
            this.Meniu.Controls.Add(this.Citire);
            this.Meniu.Dock = System.Windows.Forms.DockStyle.Top;
            this.Meniu.Location = new System.Drawing.Point(0, 0);
            this.Meniu.Name = "Meniu";
            this.Meniu.Size = new System.Drawing.Size(1011, 53);
            this.Meniu.TabIndex = 0;
            // 
            // Desen
            // 
            this.Desen.Appearance = System.Windows.Forms.Appearance.Button;
            this.Desen.AutoSize = true;
            this.Desen.Location = new System.Drawing.Point(124, 15);
            this.Desen.Name = "Desen";
            this.Desen.Size = new System.Drawing.Size(37, 23);
            this.Desen.TabIndex = 9;
            this.Desen.Text = "Graf";
            this.Desen.UseVisualStyleBackColor = true;
            this.Desen.CheckedChanged += new System.EventHandler(this.Desen_CheckedChanged);
            // 
            // FrontInf
            // 
            this.FrontInf.Appearance = System.Windows.Forms.Appearance.Button;
            this.FrontInf.AutoSize = true;
            this.FrontInf.Location = new System.Drawing.Point(458, 15);
            this.FrontInf.Name = "FrontInf";
            this.FrontInf.Size = new System.Drawing.Size(105, 23);
            this.FrontInf.TabIndex = 8;
            this.FrontInf.Text = "Frontiera Inferioara";
            this.FrontInf.UseVisualStyleBackColor = true;
            this.FrontInf.CheckedChanged += new System.EventHandler(this.FrontInf_CheckedChanged);
            // 
            // FrontSup
            // 
            this.FrontSup.Appearance = System.Windows.Forms.Appearance.Button;
            this.FrontSup.AutoSize = true;
            this.FrontSup.Location = new System.Drawing.Point(325, 15);
            this.FrontSup.Name = "FrontSup";
            this.FrontSup.Size = new System.Drawing.Size(112, 23);
            this.FrontSup.TabIndex = 7;
            this.FrontSup.Text = "Frontiera Superioara";
            this.FrontSup.UseVisualStyleBackColor = true;
            this.FrontSup.CheckedChanged += new System.EventHandler(this.FrontSup_CheckedChanged);
            // 
            // Result
            // 
            this.Result.Location = new System.Drawing.Point(584, 15);
            this.Result.Name = "Result";
            this.Result.Size = new System.Drawing.Size(75, 23);
            this.Result.TabIndex = 6;
            this.Result.Text = "Rezultat";
            this.Result.UseVisualStyleBackColor = true;
            this.Result.Click += new System.EventHandler(this.Result_Click);
            // 
            // Clear
            // 
            this.Clear.Location = new System.Drawing.Point(680, 15);
            this.Clear.Name = "Clear";
            this.Clear.Size = new System.Drawing.Size(75, 23);
            this.Clear.TabIndex = 3;
            this.Clear.Text = "Clear";
            this.Clear.UseVisualStyleBackColor = true;
            this.Clear.Click += new System.EventHandler(this.Clear_Click);
            // 
            // Apropiat
            // 
            this.Apropiat.Appearance = System.Windows.Forms.Appearance.Button;
            this.Apropiat.AutoSize = true;
            this.Apropiat.Location = new System.Drawing.Point(182, 15);
            this.Apropiat.Name = "Apropiat";
            this.Apropiat.Size = new System.Drawing.Size(122, 23);
            this.Apropiat.TabIndex = 2;
            this.Apropiat.Text = "Cel mai apropiat punct";
            this.Apropiat.UseVisualStyleBackColor = true;
            this.Apropiat.CheckedChanged += new System.EventHandler(this.Apropiat_CheckedChanged);
            // 
            // Citire
            // 
            this.Citire.Appearance = System.Windows.Forms.Appearance.Button;
            this.Citire.AutoSize = true;
            this.Citire.Location = new System.Drawing.Point(22, 15);
            this.Citire.Name = "Citire";
            this.Citire.Size = new System.Drawing.Size(81, 23);
            this.Citire.TabIndex = 1;
            this.Citire.Text = "Citirea datelor";
            this.Citire.UseVisualStyleBackColor = true;
            this.Citire.CheckedChanged += new System.EventHandler(this.Citire_CheckedChanged);
            // 
            // canvas
            // 
            this.canvas.BackColor = System.Drawing.SystemColors.Window;
            this.canvas.Dock = System.Windows.Forms.DockStyle.Fill;
            this.canvas.ForeColor = System.Drawing.Color.Snow;
            this.canvas.Location = new System.Drawing.Point(0, 53);
            this.canvas.Name = "canvas";
            this.canvas.Size = new System.Drawing.Size(1011, 443);
            this.canvas.TabIndex = 1;
            this.canvas.Paint += new System.Windows.Forms.PaintEventHandler(this.Canvas_Paint);
            // 
            // Comentarii
            // 
            this.Comentarii.BackColor = System.Drawing.Color.Moccasin;
            this.Comentarii.Dock = System.Windows.Forms.DockStyle.Right;
            this.Comentarii.FormattingEnabled = true;
            this.Comentarii.Location = new System.Drawing.Point(730, 53);
            this.Comentarii.Name = "Comentarii";
            this.Comentarii.Size = new System.Drawing.Size(281, 443);
            this.Comentarii.TabIndex = 2;
            // 
            // Window
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.ClientSize = new System.Drawing.Size(1011, 496);
            this.Controls.Add(this.Comentarii);
            this.Controls.Add(this.canvas);
            this.Controls.Add(this.Meniu);
            this.Name = "Window";
            this.ShowIcon = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Proiect Geometrie";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.Meniu.ResumeLayout(false);
            this.Meniu.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel Meniu;
        private System.Windows.Forms.Panel canvas;
        private System.Windows.Forms.ListBox Comentarii;
        private System.Windows.Forms.CheckBox Citire;
        private System.Windows.Forms.CheckBox Apropiat;
        private System.Windows.Forms.Button Clear;
        private System.Windows.Forms.Button Result;
        private System.Windows.Forms.CheckBox FrontSup;
        private System.Windows.Forms.CheckBox FrontInf;
        private System.Windows.Forms.CheckBox Desen;
    }
}

