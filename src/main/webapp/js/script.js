var openSelectDip = function() {
	var elementDip = document.getElementById("optionsDip")
	// element.style.display = (element.style.display === "block" ? "none" : "block")
	if (elementDip.classList.contains('open')) {
		elementDip.classList.remove('open')
		elementDip.classList.add('close')
	} else if (elementDip.classList.contains('close')) {
		elementDip.classList.remove('close')
		elementDip.classList.add('open')
	}

	var selectedDip = document.getElementById("selectedDip")
	if (selectedDip.classList.contains('open')) selectedDip.classList.remove('open')
	else selectedDip.classList.add('open')

}	
var setEventHandlersDip = function() {
	var optionsDip = document.getElementsByClassName("optionDip")
	Array.from(optionsDip).forEach(function(elementDip) {
		elementDip.addEventListener("click", function() {
			var settableValue = this.innerHTML
			document.getElementById("selectedValueDip").value = settableValue
			document.getElementById("selectedDip").innerHTML = settableValue

			openSelectDip();
		})
	})
}

var openSelectPrj = function() {
	var elementPrj = document.getElementById("optionsPrj")
	// element.style.display = (element.style.display === "block" ? "none" : "block")
	if (elementPrj.classList.contains('open')) {
		elementPrj.classList.remove('open')
		elementPrj.classList.add('close')
	} else if (elementPrj.classList.contains('close')) {
		elementPrj.classList.remove('close')
		elementPrj.classList.add('open')
	}

	var selectedPrj = document.getElementById("selectedPrj")
	if (selectedPrj.classList.contains('open')) selectedPrj.classList.remove('open')
	else selectedPrj.classList.add('open')

}	
var setEventHandlersPrj = function() {
	var optionsPrj = document.getElementsByClassName("optionPrj")
	Array.from(optionsPrj).forEach(function(elementPrj) {
		elementPrj.addEventListener("click", function() {
			var settableValue = this.innerHTML
			document.getElementById("selectedValuePrj").value = settableValue
			document.getElementById("selectedPrj").innerHTML = settableValue

			openSelectPrj();
		})
	})
}

var openSelectPersonalPrj = function() {
	var elementPersonalPrj = document.getElementById("optionsPersonalPrj")
	// element.style.display = (element.style.display === "block" ? "none" : "block")
	if (elementPersonalPrj.classList.contains('open')) {
		elementPersonalPrj.classList.remove('open')
		elementPersonalPrj.classList.add('close')
	} else if (elementPersonalPrj.classList.contains('close')) {
		elementPersonalPrj.classList.remove('close')
		elementPersonalPrj.classList.add('open')
	}

	var selectedPersonalPrj = document.getElementById("selectedPersonalPrj")
	if (selectedPersonalPrj.classList.contains('open')) selectedPersonalPrj.classList.remove('open')
	else selectedPersonalPrj.classList.add('open')

}	
var setEventHandlersPersonalPrj = function() {
	var optionsPersonalPrj = document.getElementsByClassName("optionPersonalPrj")
	Array.from(optionsPersonalPrj).forEach(function(elementPersonalPrj) {
		elementPersonalPrj.addEventListener("click", function() {
			var settableValue = this.innerHTML
			document.getElementById("selectedValuePersonalPrj").value = settableValue
			document.getElementById("selectedPersonalPrj").innerHTML = settableValue

			openSelectPersonalPrj();
		})
	})
}

function start() {
	setEventHandlersDip();
	setEventHandlersPrj();
}

function goBack() {
  window.history.back();
}