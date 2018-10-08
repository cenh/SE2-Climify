<style>
    .view-roomX{
        width: 100%;
        height: 100%;
        padding: 0px;
        margin: 0px;
        position: relative;
        background-color: #00caf2;
    }

    .roomx-layout{
        margin: 0px;
        position: absolute;
        width: 100%;
        height: 50%;
        border: #9933ff;
    }

    #temp{
        margin: 0;
        top: 50%;
        left: 50%;
        -ms-transform: translate(-50%, -50%);
        transform: translate(-50%, -50%);
    }
    #my_button{
        margin: 0;
        top: 50%;
        left: 50%;
        -ms-transform: translate(-50%, -50%);
        transform: translate(-50%, -50%);
    }
</style>

<div class="single-view view-roomX" align="center">
    <div class="roomx-layout">
        <form id="temp">
            Value:<br>
            <input type="number" name="value">
        </form>
    </div>
    <div class="roomx-layout">
        <button id="my_button">
            my button
        </button>
    </div>
</div>
