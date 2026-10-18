<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*,com.onlinequiz.model.*" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/admin/login.jsp");
        return;
    }
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    List<Question> questions = (List<Question>) request.getAttribute("questions");
    Question selectedQuestion = (Question) request.getAttribute("selectedQuestion");
    if (questions == null) questions = new ArrayList<Question>();
%>
<!DOCTYPE html>
<html>
<head>
<title>Edit Questions</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<main class="container page" style="max-width:760px">
  <div class="form-card" style="max-width:100%">
    <h2>Edit Quiz Questions</h2>
    <% if (quiz != null) { %>
      <p class="form-sub"><b><%=quiz.getTitle()%></b> — select a question, edit its details, and save to MySQL.</p>
    <% } %>
    <% if (request.getParameter("updated") != null) { %><div class="alert success">Question updated and saved permanently.</div><% } %>
    <% if (request.getParameter("error") != null) { %><div class="alert"><%=request.getParameter("error")%></div><% } %>
    <% if (request.getAttribute("error") != null) { %><div class="alert"><%=request.getAttribute("error")%></div><% } %>

    <% if (quiz == null) { %>
      <div class="alert">Quiz not found.</div>
    <% } else if (questions.isEmpty()) { %>
      <div class="alert warning">This quiz does not have any questions yet. Add questions first.</div>
      <a class="btn" href="<%=request.getContextPath()%>/admin/addQuestion.jsp?quizId=<%=quiz.getQuizId()%>">Add Questions</a>
    <% } else { %>
      <section class="edit-question-navigator" aria-label="Question number navigation">
        <div class="edit-question-navigator-head">
          <div>
            <h3>All Questions</h3>
            <p><%=questions.size()%> question<%=questions.size() == 1 ? "" : "s"%> in this quiz. Select a number to edit that question.</p>
          </div>
          <span class="question-count-badge"><%=questions.size()%> Total</span>
        </div>
        <div class="edit-question-number-grid">
          <% for (int i = 0; i < questions.size(); i++) {
               Question q = questions.get(i);
               boolean active = selectedQuestion != null && selectedQuestion.getQuestionId() == q.getQuestionId();
          %>
            <a class="edit-question-number<%=active ? " active" : ""%>"
               href="<%=request.getContextPath()%>/manageQuestions?quizId=<%=quiz.getQuizId()%>&questionId=<%=q.getQuestionId()%>"
               aria-label="Edit question <%=i+1%>" <%=active ? "aria-current=page" : ""%>><%=i+1%></a>
          <% } %>
        </div>
      </section>

      <div class="form-group">
        <label for="questionSelector">Select Question to Edit</label>
        <select id="questionSelector" onchange="if(this.value){window.location.href='<%=request.getContextPath()%>/manageQuestions?quizId=<%=quiz.getQuizId()%>&questionId='+this.value;}">
          <% for (int i = 0; i < questions.size(); i++) {
               Question q = questions.get(i);
          %>
            <option value="<%=q.getQuestionId()%>" <%=selectedQuestion != null && selectedQuestion.getQuestionId() == q.getQuestionId() ? "selected" : ""%>>
              Question <%=i+1%> — <%=q.getQuestionText()%>
            </option>
          <% } %>
        </select>
      </div>

      <% if (selectedQuestion != null) {
          int correctIndex = 0;
          List<Option> options = selectedQuestion.getOptions();
          if (options == null) options = new ArrayList<Option>();
          for (int i = 0; i < options.size(); i++) if (options.get(i).isCorrect()) correctIndex = i;
      %>
      <form method="post" action="<%=request.getContextPath()%>/updateQuestion">
        <input type="hidden" name="quizId" value="<%=quiz.getQuizId()%>">
        <input type="hidden" name="questionId" value="<%=selectedQuestion.getQuestionId()%>">
        <div class="form-group">
          <label for="questionText">Question Text</label>
          <textarea id="questionText" name="questionText" required><%=selectedQuestion.getQuestionText()%></textarea>
        </div>
        <div class="form-group">
          <label for="marks">Marks</label>
          <input id="marks" type="number" name="marks" min="1" value="<%=selectedQuestion.getMarks()%>" required>
        </div>
        <% for (int i = 0; i < 4; i++) { String optionText = i < options.size() ? options.get(i).getOptionText() : ""; %>
          <div class="form-group">
            <label for="option<%=i+1%>">Option <%=i+1%></label>
            <input id="option<%=i+1%>" name="option<%=i+1%>" value="<%=optionText%>" required maxlength="255">
          </div>
        <% } %>
        <div class="form-group">
          <label for="correct">Correct Option</label>
          <select id="correct" name="correct" required>
            <% for (int i = 0; i < 4; i++) { %>
              <option value="<%=i%>" <%=correctIndex == i ? "selected" : ""%>>Option <%=i+1%></option>
            <% } %>
          </select>
        </div>
        <div class="btn-row">
          <button class="btn btn-success" type="submit">Save Question Changes</button>
          <a class="btn btn-secondary" href="<%=request.getContextPath()%>/adminDashboard">Back to Dashboard</a>
        </div>
      </form>
      <% } %>
    <% } %>
  </div>
</main>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
