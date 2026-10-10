/* ==========================================================================
   Online Quiz System — front-end behaviour
   Pure UI/UX layer: nothing here touches form field names, action URLs,
   or submitted values, so the existing servlets keep working unchanged.
   ========================================================================== */
(function () {
  "use strict";

  /* ---------------- Mobile navigation ---------------- */
  function initNav() {
    var toggle = document.getElementById("navToggle");
    var links = document.getElementById("navLinks");
    if (toggle && links) {
      toggle.addEventListener("click", function () {
        var open = links.classList.toggle("open");
        toggle.classList.toggle("open", open);
        toggle.setAttribute("aria-expanded", open ? "true" : "false");
      });
      links.querySelectorAll("a").forEach(function (a) {
        a.addEventListener("click", function () {
          links.classList.remove("open");
          toggle.classList.remove("open");
        });
      });
    }

    // Highlight the nav link matching the current page
    var path = window.location.pathname.replace(/\/+$/, "");
    document.querySelectorAll(".nav-links a[href]").forEach(function (a) {
      try {
        var linkPath = new URL(a.href, window.location.origin).pathname.replace(/\/+$/, "");
        if (linkPath && (linkPath === path || (linkPath.length > 1 && path.indexOf(linkPath) === 0))) {
          a.classList.add("active");
        }
      } catch (e) { /* ignore malformed hrefs */ }
    });
  }

  /* ---------------- Auto-dismiss alerts ---------------- */
  function initAlerts() {
    document.querySelectorAll(".alert.success").forEach(function (el) {
      setTimeout(function () {
        el.style.transition = "opacity .4s ease";
        el.style.opacity = "0";
        setTimeout(function () { el.style.display = "none"; }, 400);
      }, 4500);
    });
  }

  /* ---------------- Generic client-side search filter ----------------
     Filters visible .quiz-card / table rows by text content.
     Never touches data — purely show/hide, so nothing server-side changes. */
  function initFilters() {
    document.querySelectorAll("[data-filter-target]").forEach(function (input) {
      var targetSelector = input.getAttribute("data-filter-target");
      var itemSelector = input.getAttribute("data-filter-item");
      input.addEventListener("input", function () {
        var q = input.value.trim().toLowerCase();
        document.querySelectorAll(targetSelector + " " + itemSelector).forEach(function (item) {
          var text = item.textContent.toLowerCase();
          item.style.display = text.indexOf(q) === -1 ? "none" : "";
        });
      });
    });
  }

  /* ---------------- Quiz attempt experience ---------------- */
  function initQuizAttempt() {
    var form = document.querySelector(".quiz-attempt-form");
    if (!form) return;

    var cards = Array.prototype.slice.call(form.querySelectorAll(".question-card"));
    if (!cards.length) return;

    var paletteGrid = document.getElementById("paletteGrid");
    var progressFill = document.getElementById("quizProgressFill");
    var progressLabel = document.getElementById("quizProgressLabel");
    var current = 0;

    // Build the palette (one numbered button per question)
    var paletteButtons = [];
    if (paletteGrid) {
      cards.forEach(function (card, idx) {
        var btn = document.createElement("button");
        btn.type = "button";
        btn.className = "palette-btn";
        btn.textContent = String(idx + 1);
        btn.addEventListener("click", function () { showQuestion(idx); });
        paletteGrid.appendChild(btn);
        paletteButtons.push(btn);
      });
    }

    function isAnswered(card) {
      return !!card.querySelector("input[type=radio]:checked");
    }

    function refreshPalette() {
      cards.forEach(function (card, idx) {
        var btn = paletteButtons[idx];
        if (!btn) return;
        btn.classList.toggle("answered", isAnswered(card));
        btn.classList.toggle("current", idx === current);
      });
    }

    function refreshProgress() {
      var answeredCount = cards.filter(isAnswered).length;
      if (progressFill) progressFill.style.width = Math.round((answeredCount / cards.length) * 100) + "%";
      if (progressLabel) progressLabel.textContent = "Question " + (current + 1) + " of " + cards.length;
    }

    function showQuestion(idx) {
      if (idx < 0 || idx >= cards.length) return;
      cards[current].style.display = "none";
      current = idx;
      cards[current].style.display = "";
      cards[current].scrollIntoView({ behavior: "smooth", block: "start" });
      refreshPalette();
      refreshProgress();
      var prevBtn = document.getElementById("quizPrevBtn");
      var nextBtn = document.getElementById("quizNextBtn");
      if (prevBtn) prevBtn.disabled = current === 0;
      if (nextBtn) nextBtn.style.display = current === cards.length - 1 ? "none" : "";
    }

    cards.forEach(function (card) {
      card.addEventListener("change", function () { refreshPalette(); refreshProgress(); });
    });

    var prevBtn = document.getElementById("quizPrevBtn");
    var nextBtn = document.getElementById("quizNextBtn");
    if (prevBtn) prevBtn.addEventListener("click", function () { showQuestion(current - 1); });
    if (nextBtn) nextBtn.addEventListener("click", function () { showQuestion(current + 1); });

    // Initial state: show only the first question
    cards.forEach(function (card, idx) { card.style.display = idx === 0 ? "" : "none"; });
    showQuestion(0);

    /* ---- Submit confirmation modal (front-end only; same form.submit()) ---- */
    var modal = document.getElementById("submitModal");
    var modalText = document.getElementById("submitModalText");
    var confirmBtn = document.getElementById("confirmSubmitBtn");
    var cancelBtn = document.getElementById("cancelSubmitBtn");
    var submitBtn = form.querySelector("button[type=submit]");

    if (submitBtn && modal) {
      submitBtn.addEventListener("click", function (e) {
        e.preventDefault();
        var unanswered = cards.length - cards.filter(isAnswered).length;
        if (modalText) {
          modalText.textContent = unanswered > 0
            ? "You still have " + unanswered + " unanswered question" + (unanswered === 1 ? "" : "s") + ". Submit anyway?"
            : "You've answered every question. Submit your quiz now?";
        }
        modal.classList.add("open");
      });
      if (cancelBtn) cancelBtn.addEventListener("click", function () { modal.classList.remove("open"); });
      if (confirmBtn) confirmBtn.addEventListener("click", function () { modal.classList.remove("open"); form.submit(); });
      modal.addEventListener("click", function (e) { if (e.target === modal) modal.classList.remove("open"); });
    }

    /* ---- Visual countdown timer (front-end only) ----
       Purely cosmetic/UX: the server does not currently enforce a time
       limit, so this cannot change scoring or submission logic — it just
       submits the same form automatically once the visible time runs out. */
    var timerEl = document.getElementById("quizTimer");
    var timerText = document.getElementById("quizTimerText");
    if (timerEl && timerText) {
      var minutes = parseInt(timerEl.getAttribute("data-duration-min"), 10);
      if (!isNaN(minutes) && minutes > 0) {
        var remaining = minutes * 60;
        var tick = function () {
          var m = Math.floor(remaining / 60);
          var s = remaining % 60;
          timerText.textContent = (m < 10 ? "0" : "") + m + ":" + (s < 10 ? "0" : "") + s;
          timerEl.classList.toggle("timer-warn", remaining <= 300 && remaining > 60);
          timerEl.classList.toggle("timer-danger", remaining <= 60);
          if (remaining <= 0) {
            clearInterval(interval);
            form.submit();
            return;
          }
          remaining--;
        };
        tick();
        var interval = setInterval(tick, 1000);
      }
    }
  }

  /* ---------------- Animated stat counters ---------------- */
  function initStatCounters() {
    document.querySelectorAll(".stat-value[data-count]").forEach(function (el) {
      var target = parseInt(el.getAttribute("data-count"), 10);
      if (isNaN(target)) return;
      var start = 0;
      var duration = 600;
      var startTime = null;
      function step(ts) {
        if (!startTime) startTime = ts;
        var progress = Math.min((ts - startTime) / duration, 1);
        el.textContent = Math.round(start + (target - start) * progress);
        if (progress < 1) requestAnimationFrame(step);
      }
      requestAnimationFrame(step);
    });
  }

  document.addEventListener("DOMContentLoaded", function () {
    initNav();
    initAlerts();
    initFilters();
    initQuizAttempt();
    initStatCounters();
  });
})();
